(ns val-date-calc.fwd
  ( :require [clj-time.core :as dt]
             [val-date-calc.core :as c]))

(defn last-bus-day [date id1 id2 ccys]
  (loop [d (c/make-local (dt/last-day-of-the-month (dt/year date)
                                                   (dt/month date)))]
    (if (c/settle-day? d id1 id2 ccys)
      d
      (recur (c/dec-day d)))))

(defn last-bus-day? [spot id1 id2 ccys]
  (= spot (last-bus-day spot id1 id2 ccys)))

(defn fwd-date
  [tenor count spot id1 id2 ccys]

  (defn month-date
    [count]
    (let [lbd (last-bus-day? spot id1 id2 ccys)
          d   (dt/plus spot (dt/months count))
          pd  (c/inc-to-val-date d id1 id2 ccys)]
      (if (or lbd (not (= (dt/month d) (dt/month pd))))
        (last-bus-day d id1 id2 ccys)
        pd)))

  (defn day-date
    [count]
    (loop [date (dt/plus spot (dt/days count))]
      (if (c/settle-day? date id1 id2 ccys)
        date
        (recur (c/inc-day date)))))

  (defn week-date
    [count]
    (loop [date (dt/plus spot (dt/weeks count))]
      (if (c/settle-day? date id1 id2 ccys)
        date
        (recur (c/inc-day date)))))

  (cond
   (= :d tenor) (day-date count)
   (= :w tenor) (week-date count)
   (= :m tenor) (month-date count)
   (= :y tenor) (month-date (* 12 count))))
