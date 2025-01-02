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

(defn count-if [x coll]
  (count (filter #(= % x) coll)))

(defn summarize [[left right]]
  (reduce +
    (map
      (fn [x] (* x (count-if x right)))
      left
    )
  )
)

(println (
 summarize
 (transpose (read-all-pairs))
))
