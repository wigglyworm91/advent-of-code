(ns day3.part1
  (:require [lib.lib :as lib]))

(println
  (reduce +
    (mapcat
      (fn [ln]
        (map
          (fn [[_ x y]]
            (* (parse-long x) (parse-long y))
          )
          (re-seq #"mul\((\d\d?\d?),(\d\d?\d?)\)" ln)
        )
      )
      (line-seq (java.io.BufferedReader. *in*))
    )
  )
)
