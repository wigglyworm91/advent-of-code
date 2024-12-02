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

(println
  (reduce +
    (map
      (fn [[a b]] (abs (- a b)))
      (transpose
        (map
          sort
          (transpose
            (read-all-pairs)
          )
        )
      )
    )
  )
)
