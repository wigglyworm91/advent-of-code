(ns day10.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(defn step [grid n]
  ; goal: construct a new grid similar to the input except
  ; for each cell, if it is equal to n+1 and NOT adjacent to something with n,
  ; then it will be nil
  ; also if it's equal to n then it will be nil
  (for [y (range (count grid))]
    (for [x (range (count (first grid)))]
      (let [value (lib/nnth-safe grid y x)]
        (cond
          (= value n) nil
          (= value (inc n))
            (cond
              (= n (lib/nnth-safe grid (dec y) x)) value
              (= n (lib/nnth-safe grid (inc y) x)) value
              (= n (lib/nnth-safe grid y (dec x))) value
              (= n (lib/nnth-safe grid y (inc x))) value
              :else nil
            )
          :else value
        )
      )
    )
  )
)

(defn debug-grid [grid]
  (doseq [y (range (count grid))]
    (doseq [x (range (count (first grid)))]
      (let [value (lib/nnth-safe grid y x)]
        (print
          (if (some? value) value ".")
        )
      )
    )
    (println)
  )
  (println)
  grid
)

(defn count-9s [grid]
  (->> grid
       (mapcat identity)
       (filter #(= 9 %))
       count
  )
)

(-> (lib/read-grid)
  debug-grid
  (step 0)
  debug-grid
  (step 1)
  debug-grid
  (step 2)
  debug-grid
  (step 3)
  debug-grid
  (step 4)
  debug-grid
  (step 5)
  debug-grid
  (step 6)
  debug-grid
  (step 7)
  debug-grid
  (step 8)
  debug-grid
  count-9s
  println
)
