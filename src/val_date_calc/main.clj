(ns val-date-calc.main
  (:require [clj-time.core :as dt]
            [clj-time.local :as l]
            [val-date-calc.core :as c]
            [val-date-calc.fwd :as f]
            [val-date-calc.spot :as s]))

(defn parse-tenor [tenor]
  (let [[_ c t] (first (re-seq #"([0-9]{1,2})([D,W,M,Y])" tenor))
        fd {"D" :d "W" :w "M" :m "Y" :y}
        nr {"SP" :sp "ON" :on "TN" :tn "SN" :sn}]
    (if (nil? c)
      [(nr tenor) nil]
      [(fd t) (Integer/parseInt c)])))

(defn cutoffs [date ccy]
  (let [[rs rpr] (:day-ends ccy)
        y        (dt/year  date)
        m        (dt/month date)
        d        (dt/day   date)]
    [(dt/date-time y m d rs)
     (dt/date-time y m d rpr)]))

(defn t-date
  [t-date-time id ccys]
  (let [ccy          (ccys id)
        ccy-date     (dt/to-time-zone t-date-time
                                      (dt/time-zone-for-id (:tz ccy)))
        today        (c/make-local ccy-date)
        tomorrow     (c/inc-day today)
        [ctf pr-ctf] (cutoffs ccy-date ccy)]
    (if (c/working-day? ccy tomorrow)
      (if (dt/after? ccy-date ctf) tomorrow today)
      (if (dt/after? ccy-date pr-ctf) tomorrow today))))

(defn val-date
  [tenor t-dt-time id1 id2 ccys]
  (let [t-dt1 (t-date t-dt-time id1 ccys)
        t-dt2 (t-date t-dt-time id2 ccys)
        spot  (s/spot-date t-dt1 t-dt2 id1 id2 ccys)
        [t c] (parse-tenor tenor)]
    (cond
     (= :sp t) spot
     (not (nil? c)) (f/fwd-date t c spot id1 id2 ccys))))

