(ns val-date-calc.core-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as dt]
            [val-date-calc.core :refer :all]))

(def ccys
  {"AED" (->Ccy "AED" 2 #{5}   #{} #{"USD"} #{} [17 17] "US/Eastern")
   "AUD" (->Ccy "AUD" 2 #{6 7} #{} #{"USD"} #{} [17 17] "US/Eastern")
   "CAD" (->Ccy "CAD" 1 #{6 7} #{(dt/local-date 2000  4 24)} #{"USD"} #{} [17 17] "US/Eastern")
   "GBP" (->Ccy "GBP" 2 #{6 7} #{(dt/local-date 2000  4 24) 
                                 (dt/local-date 2000  5 31)} #{"USD"} #{} [17 17] "US/Eastern")
   "JPY" (->Ccy "JPY" 2 #{6 7} #{(dt/local-date 2000  5 10) 
                                 (dt/local-date 2000  5 18) 
                                 (dt/local-date 2000  5 24)
                                 (dt/local-date 2000  5 25) 
                                 (dt/local-date 2000  6  1)} #{"USD"} #{} [17 17] "US/Eastern")
   "MXN" (->Ccy "MXN" 1 #{6 7} #{(dt/local-date 2000  5  8)
                                 (dt/local-date 2000  5 15)} #{"USD"} #{"USD"} [17 17] "US/Eastern")
   "NZD" (->Ccy "NZD" 2 #{6 7} #{(dt/local-date 2000  4 19)} #{"USD"} #{} [15 17] "US/Eastern")
   "RUB" (->Ccy "RUB" 2 #{6 7} #{} #{"USD"} #{} [17 17] "US/Eastern")
   "SAR" (->Ccy "SAR" 2 #{5}   #{} #{"USD"} #{} [17 17] "US/Eastern")
   "USD" (->Ccy "USD" 1 #{6 7} #{(dt/local-date 2000  5  3) 
                                 (dt/local-date 2000  5 17)} #{} #{} [17 17] "US/Eastern")})

