(ns val-date-calc.fwd-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as dt]
            [val-date-calc.core :refer :all]
            [val-date-calc.main :refer :all]
            [val-date-calc.fwd :refer :all]
            [val-date-calc.core-test :refer :all]))

(deftest day-fwd
  (testing "No Holidays"    
    (are [x y] (= x y)
         (val-date "1D" (dt/date-time 2000 4 18 12) "CAD" "USD" ccys)
         (dt/local-date 2000 4 20)

         (val-date "1D" (dt/date-time 2000 4 18 12) "GBP" "JPY" ccys)
         (dt/local-date 2000 4 21)

         (val-date "1W" (dt/date-time 2000 6  5 12) "USD" "CAD" ccys)
         (dt/local-date 2000 6 13)

         (val-date "1W" (dt/date-time 2000 6  5 12) "GBP" "USD" ccys)
         (dt/local-date 2000 6 14)

         (val-date "1M" (dt/date-time 2000 6  5 12) "USD" "CAD" ccys)
         (dt/local-date 2000 7 6)

         (val-date "1M" (dt/date-time 2000 6  5 12) "GBP" "USD" ccys)
         (dt/local-date 2000 7 7)

         (val-date "2M" (dt/date-time 2000 6  5 12) "USD" "CAD" ccys)
         (dt/local-date 2000 8 7)

         (val-date "1Y" (dt/date-time 2000 6  5 12) "USD" "CAD" ccys)
         (dt/local-date 2001 6  6)

         (val-date "1Y" (dt/date-time 2000 6  5 12) "GBP" "USD" ccys)
         (dt/local-date 2001 6  7)

         (val-date "1M" (dt/date-time 2000 2 27 12) "USD" "CAD" ccys)
         (dt/local-date 2000 3 31)

         (val-date "SP" (dt/date-time 2000 2 27 12) "USD" "CAD" ccys)
         (dt/local-date 2000 2 29)
         ))

  (testing "With Holidays"    
    (are [x y] (= x y)
         (val-date "2M" (dt/date-time 2000 5  8 12) "USD" "JPY" ccys)
         (dt/local-date 2000 7 11)

         (val-date "2M" (dt/date-time 2000 5  8 12) "GBP" "USD" ccys)
         (dt/local-date 2000 7 10)

         (val-date "1W" (dt/date-time 2000 5  8 12) "USD" "JPY" ccys)
         (dt/local-date 2000 5 19)

         (val-date "1M" (dt/date-time 2000 3 30 12) "GBP" "USD" ccys)
         (dt/local-date 2000 5  4)
         ))

  (testing "Sticky moth end"    
    (are [x y] (= x y)
         (val-date "1M" (dt/date-time 2000 1 27 12) "USD" "JPY" ccys)
         (dt/local-date 2000 2 29)

         (val-date "3M" (dt/date-time 2000 1 27 12) "GBP" "JPY" ccys)
         (dt/local-date 2000 4 28)

         (val-date "1M" (dt/date-time 2000 2 25 12) "GBP" "USD" ccys)
         (dt/local-date 2000 3 31)

         (val-date "2M" (dt/date-time 2000 2 25 12) "USD" "JPY" ccys)
         (dt/local-date 2000 4 28)

         (val-date "3M" (dt/date-time 2000 2 25 12) "GBP" "USD" ccys)
         (dt/local-date 2000 5 30)

         (val-date "1M" (dt/date-time 2000 3 28 12) "USD" "JPY" ccys)
         (dt/local-date 2000 4 28)
)))
