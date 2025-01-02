(ns lib.lib)

(require '[clojure.string :as str])

(defn transpose [m]
  (apply mapv vector m))

(defn read-pair []
  (map parse-long (str/split (read-line) #" +"))
)

(defn read-all-pairs []
  (take-while some? (repeatedly #(try
    (read-pair)
    (catch Exception _ nil)
  )))
)

(defn read-all-lines []
  (take-while some? (repeatedly #(read-line))))

(defn pairs [l]
  (partition 2 1 l))

(defn increasing? [l]
  (apply <= l)
)
(defn decreasing? [l]
  (apply >= l)
)

(defn sum [coll] (reduce + coll))

(defn c2i [c] (- (int c) (int \0)))

(defn read-grid []
  (for [line (read-all-lines)]
    (mapv c2i line)
  )
)

(defn nnth-safe [grid y x & [default]]
  (let [default (or default nil)]
    (cond
      (< y 0) default
      (< x 0) default
      (>= y (count grid)) default
      (>= x (count (nth grid y))) default
      :else (nth (nth grid y) x)
    )
  )
)
