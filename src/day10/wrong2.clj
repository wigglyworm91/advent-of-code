(ns day10.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(def grid (lib/read-grid))

(defn indices [grid]
  (for [y (range (count grid))]
    (for [x (range (count (first grid)))]
      [y x]
    )
  )
)

(defn step [score n]
  ; goal: propagate the score information to surrounding nodes
  ; for each cell, if that cell is equal to n,
  ; its score is the sum of its neighbors' scores
  ; otherwise it's 0
  (for [y (range (count grid))]
    (for [x (range (count (first grid)))]
      (let [value (lib/nnth-safe grid y x)]
        (if (= value n)
          (+
            (lib/nnth-safe score (dec y) x 0)
            (lib/nnth-safe score (inc y) x 0)
            (lib/nnth-safe score y (dec x) 0)
            (lib/nnth-safe score y (inc x) 0)
          )
          0
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
          (cond
            (nil? value) "."
            (= 0 value) "."
            (<= 1 value 9) value
            (<= 10 value 35) (char (+ (int \a) (- value 10)))
            :else "+"
          )
        )
      )
    )
    (println)
  )
  (println)
  grid
)

(def scores
  (for [y (range (count grid))]
    (for [x (range (count (first grid)))]
      (if (= 9 (lib/nnth-safe grid y x))
        1
        0
      )
    )
  )
)

(defn sum-grid [grid]
  (lib/sum (mapcat identity grid))
)


(-> scores
  (step 8)
  (step 7)
  (step 6)
  (step 5)
  (step 4)
  (step 3)
  (step 2)
  (step 1)
  (step 0)
  sum-grid
  println
)
