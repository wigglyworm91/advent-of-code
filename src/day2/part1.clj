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

(defn pairs [l]
  (partition 2 1 l))

(defn increasing? [l]
  (apply <= l)
)
(defn decreasing? [l]
  (apply >= l)
)

(defn gap [[a b]] (abs (- a b)))

(defn safe? [l]
  (and
    (or (increasing? l) (decreasing? l))
    (every? (fn [x] (<= 1 x 3))
      (map gap (pairs l))
    )
  )
)

(assert      (safe? '(7 6 4 2 1)))
(assert (not (safe? '(1 2 7 8 9))))
(assert (not (safe? '(9 7 6 2 1))))
(assert (not (safe? '(1 3 2 4 5))))
(assert (not (safe? '(8 6 4 4 1))))
(assert      (safe? '(1 3 6 7 9)))

;;(->> (read-all-pairs)
;;     (filter safe?)
;;     count
;;     println)

(defn without [coll n]
  (concat (take n coll) (drop (inc n) coll)))

(defn damp-safe? [coll]
  (some safe? (map (fn [n] (without coll n)) (range (count coll)))))

(assert      (damp-safe? '(7 6 4 2 1)))
(assert (not (damp-safe? '(1 2 7 8 9))))
(assert (not (damp-safe? '(9 7 6 2 1))))
(assert      (damp-safe? '(1 3 2 4 5)))

(->> (read-all-pairs)
     (filter damp-safe?)
     count
     println)
