(ns day3.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)
  
(defn get-input []
  (str/join " " (line-seq (java.io.BufferedReader. *in*))))

;; looks for mul(x,y) and turns it into x*y (returns stream)
;; i don't like the way this function turned out tbh, kinda ugly
(defn eval-mul [line]
  (->> (re-seq #"mul\((\d\d?\d?),(\d\d?\d?)\)" line)
       (map (fn [[_ x y]] (* (parse-long x) (parse-long y))))
  )
)

(defn only-valid [line]
  (str/join " " (re-seq #"(?:^|do\(\)).+?(?:don't\(\)|$)" line))
)

(defn sum-muls []
  (->>
    (get-input)
    only-valid
    eval-mul
    (reduce +)
  )
)

(println (sum-muls))
