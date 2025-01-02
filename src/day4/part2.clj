(ns day4.part2
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(defn nnth [lines row col]
  (nth (nth lines row) col))

(defn get-x [lines row col]
  (str/join [
    (nnth lines (dec row) (dec col))
    (nnth lines (dec row) (inc col))
    (nnth lines row col)
    (nnth lines (inc row) (dec col))
    (nnth lines (inc row) (inc col))
  ])
)

(defn is-xmas [lines row col]
  (let [x (get-x lines row col)]
    (or
      (= x "MSAMS")
      (= x "MMASS")
      (= x "SMASM")
      (= x "SSAMM")
    )
  )
)

(let [lines (lib/read-all-lines)
      rows (count lines)
      cols (count (first lines))
  ]
  (->>
    (for [row (range 1 (dec rows))]
      (for [col (range 1 (dec cols))]
        (is-xmas lines row col)
      )
    )
    flatten
    (filter identity)
    count
    println
  )
)
