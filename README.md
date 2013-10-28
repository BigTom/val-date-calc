# val-date-calc

A Clojure library designed to calculate value dates, given a trade date or a specific instant.

## Usage

Still under development.

Basic usage from clojure is:

```clojure
(ns val-date-calc.spot-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as dt]
            [val-date-calc.main :refer :all]))

(val-date "SP" (dt/date-time 2000 4 18 12) "CAD" "USD" ccys)
```

Where ccys is a clojure data structure possible defined like this:

```clojure
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
```


## License

Copyright © 2013 Thomas Ayerst

Distributed under the Eclipse Public License version 1.0.
