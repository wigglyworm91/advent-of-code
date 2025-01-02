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

(defn can-be-next? [rules value]
  (every? (fn [[a b]] (not= b value)) rules)
)

(defn toposort [rules order]
  (let [order (set order)
        rules (filter (fn [[a b]] (and (contains? order a) (contains? order b))) rules)
        candidates (filter #(can-be-next? rules %) order)
        item (first candidates)
       ]
    (if (some? item)
      (cons item (toposort rules (remove #{item} order)))
      []
    )
  )
)

(let [rules  (doall (take-while #(> (count %) 0) (lib/read-all-lines)))
      orders (doall (take-while #(> (count %) 0) (lib/read-all-lines)))
      rules  (parse-int-lists rules #"\|")
      orders (parse-int-lists orders #",")
     ]
  (println (reduce + (for [order orders
        :when (not (is-valid-order rules order))
       ]
    (get-middle (toposort rules order))
  )))
)
