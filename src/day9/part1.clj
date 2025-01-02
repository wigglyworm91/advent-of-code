(ns day7.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(defn get-id [i]
  (when (even? i) (/ i 2))
)

(defn expand [input]
  (mapcat
    #(repeat
       (nth input %)
       (get-id %)
    )
    (range (count input))
  )
)

(defn compact [blocks]
  (loop [
      i 0
      j (dec (count blocks))
      out []
    ]
    ;(println i j)
    (cond
      (> i j) out
      (some? (nth blocks i)) (recur (inc i)      j  (conj out (nth blocks i)))
      (some? (nth blocks j)) (recur (inc i) (dec j) (conj out (nth blocks j)))
      :else (recur i (dec j) out)
    )
  )
)


(defn checksum [compacted]
  (lib/sum
    (map-indexed (fn [i block] (* i block)) compacted)
  )
)

(println (expand [1 2 3]))
(assert (= (expand [1 2 3]) [0 nil nil 1 1 1]))
(println (expand [2 3 3 3 1 3 3 1 2 1 4 1 4 1 3 1 4 0 2]))

(defn c2i [c] (- (int c) (int \0)))
(println (map c2i "12345"))

(defn debug [x] (println x) x)

(->> (lib/read-all-lines)
    first
    (map c2i)
    expand
    compact
    ;debug
    checksum
    println
)
