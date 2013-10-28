(ns val-date-calc.spot
  ( :require [val-date-calc.core :as c]
             [clj-time.core :as dt]
             [clojure.set :as s]))

(declare pair-spot-date ccy-spot-date trade-date)

(defn spot-date
  "local-date String String {Ccy} -> local-date
   Given a date and a currency pair returns the spot date"
  [date1 date2 id1 id2 ccys]
  ;; TODO: spot day override for ccy pair
  (pair-spot-date (ccy-spot-date (trade-date date1 id1 ccys) id1 ccys) id1
                  (ccy-spot-date (trade-date date2 id2 ccys) id2 ccys) id2
                  ccys))

(defn- pair-spot-date
  [date1 id1 date2 id2 ccys]
  (let [date (c/max-date date1 date2)]
    (c/inc-to-val-date date id1 id2 ccys)))

(defn- ccy-spot-date
  "local-date String {Ccy} -> local-date
   Given a trading date, a currency and the number of days to spot; return the
   spot-date"
  [date id ccys]
  (letfn [(i-ccy-spot-date
            [date days]
            (let [bus-day (c/business-day? date id ccys)]
              (if (and (zero? days) bus-day)
                date 
                (recur (c/inc-day date) (if bus-day (dec days) days)))))]
    ;; ignore holidays on the trade date unless zeroo spot
    (let [days (:spotdays (ccys id))]
      (if (zero? days)  
        (i-ccy-spot-date date days)
        (i-ccy-spot-date (c/inc-day date) (dec days))))))

(defn- trade-date
  "local-date String {Ccy} -> local-date
   Given a date and a currency spec return the trading date"
  [date id ccys]
  (let [ccy (ccys id)]
      (if (c/working-day? ccy date)
        date
        (recur (c/inc-day date) id ccys))))
