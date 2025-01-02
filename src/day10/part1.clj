(ns day10.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(defn indices [grid]
  (for [y (range (count grid))
        x (range (count (first grid)))
      ]
    [y x]
  )
)

(defn transmute [grid f]
  (for [y (range (count grid))]
    (for [x (range (count (first grid)))]
      (f y x (lib/nnth-safe grid y x))
    )
  )
)

(defn count-true [grid]
  (->> grid
       (mapcat identity)
       (filter true?)
       count
  )
)

(defn debug-grid [grid]
  (doseq [y (-> grid count range)]
    (doseq [x (-> grid first count range)]
      (let [v (lib/nnth-safe grid y x)]
        (print (cond
          (nil? v) "."
          (true? v) "T"
          (false? v) "."
          (zero? v) "."
          (<= 0 v 9) v
          (<= 10 v 36) (char (+ (int v) (int \0)))
          :else v
        ))
      )
    )
    (println)
  )
  (println)
  grid
)

(defn debug [x]
  (println x)
  x
)

(defn num-reachable [grid yy xx]
  ; floodfill from [y x]
  (loop [
      i 0
      reachable (transmute grid (fn [y x v] (= [yy xx] [y x])))
    ]
    ;(debug-grid reachable)
    (if (= i 9)
      (debug (count-true reachable))
      ; a square is reachable if it is equal to n+1 and any of its neighbors are reachable
      (recur
        (inc i)
        (for [y (range (count grid))]
          (for [x (range (count (first grid)))]
            (and
              (= (inc i) (lib/nnth-safe grid y x))
              (or
                (lib/nnth-safe reachable (dec y) x)
                (lib/nnth-safe reachable (inc y) x)
                (lib/nnth-safe reachable y (dec x))
                (lib/nnth-safe reachable y (inc x))
              )
            )
          )
        )
      )
    )
  )
)

(def grid (lib/read-grid))

(println
  (lib/sum
    (for [[y x] (indices grid)]
      (if (= (lib/nnth-safe grid y x) 0)
        (do
          (println y x)
          (num-reachable grid y x)
        )
        0
      )
    )
  )
)
