(ns val-date-calc.core
  ( :require [clj-time.core :as dt]
             [clj-time.format :as f]     
             [clojure.set :as s]))

(defrecord Ccy [id spotdays weekend holidays no-settle shar-hols day-ends tz])

(defn make-local [d]
  (dt/local-date (dt/year d) (dt/month d) (dt/day d)))

(defn max-date [d1 d2] (if (dt/after? d1 d2) d1 d2))

(defn inc-day [date]
  (dt/plus date (dt/days 1)))

(defn dec-day [date]
  (dt/minus date (dt/days 1)))

(defn- relevent-holidays
  "String {Ccy} keyword -> #{local-date}"
  [id ccys hol-type]
  (let [ccy (ccys id)
        ids (conj (hol-type ccy) (:id ccy))]
    (reduce (partial s/union #{})
            (map :holidays (map (partial ccys) ids)))))

(defn working-day?
  "A working day is a non-weekend day for that currency"
  [ccy date]
  (let [weekend (:weekend ccy)
        day (dt/day-of-week date)]
    (not (weekend day))))

(defn- day-type
  "local-date String {Ccy} keyword -> boolean"
  [date id ccys hol-type]
  (let [ccy (ccys id)]
    (and (working-day? ccy date)
         (not (contains? (relevent-holidays id ccys hol-type) date)))))

(defn settle-day?
  "local-date String {Ccy} -> boolean"
  ([date id ccys]
     (and (day-type date id ccys :shar-hols) (day-type date id ccys :no-settle)))
  ([date id1 id2 ccys]
     (and (settle-day? date id1 ccys) (settle-day? date id2 ccys))))

(defn- day-type
  "local-date String {Ccy} keyword -> boolean"
  [date id ccys hol-type]
  (let [ccy (ccys id)]
    (and (working-day? ccy date)
         (not (contains? (relevent-holidays id ccys hol-type) date)))))

(defn inc-to-val-date
  [date id1 id2 ccys]
  (if (settle-day? date id1 id2 ccys)
    date
    (recur (inc-day date) id1 id2 ccys)))

(defn business-day?
  "local-date String {Ccy} -> boolean
   A business day is a date which is not a weekend or holiday for that ccy"
  [date id ccys]
  (day-type date id ccys :shar-hols))

