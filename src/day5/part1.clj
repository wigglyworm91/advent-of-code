(ns day5.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(defn parse-int-lists [s sep]
  (map #(map parse-long (str/split % sep)) s)
)

(defn is-valid-order [rules order]
  ; rules: list of [a b] pairs
  ; order: list of pages
  (every? identity
    (for [[a b] rules
          :let [idx1 (.indexOf order a)
                idx2 (.indexOf order b)
               ]
          :when (and (>= idx1 0) (>= idx2 0))
         ]
      (< idx1 idx2)
    )
  )
)

(defn get-middle [coll]
  (nth coll (/ (dec (count coll)) 2))
)

(assert (= (get-middle [75 47 61 53 29]) 61))

(let [rules  (doall (take-while #(> (count %) 0) (lib/read-all-lines)))
      orders (doall (take-while #(> (count %) 0) (lib/read-all-lines)))
      rules (parse-int-lists rules #"\|")
      orders (parse-int-lists orders #",")
     ]
  ;(println rules)
  ;(println orders)
  (println (reduce + (for [order orders
        :when (is-valid-order rules order)
       ]
    (get-middle order)
  )))
  ;(println (map (partial is-valid-order rules) orders))
)
