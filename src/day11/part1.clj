(ns day11.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

; https://stackoverflow.com/a/26476844
(defn Y-mem [f]
  (let [mem (atom {})]
    (#(% %)
     (fn [x]
       (f #(if-let [e (find @mem %&)]
            (val e)
            (let [ret (apply (x x) %&)]
              (swap! mem assoc %& ret)
              ret
            )))))))

(defmacro defrecfn [name args & body]
  `(def ~name
       (Y-mem (fn [foo#]
                 (fn ~args (let [~name foo#] ~@body))))))

(defn even-digits? [value]
  (even? (count (str value)))
)

(defn split-digits [value]
  (let [digits (str value)
        size (count digits)]
    [
      (parse-long (subs digits 0 (/ size 2)))
      (parse-long (subs digits (/ size 2) size))
    ]
  )
)

(assert (= (split-digits 1000) [10 0]))

(defrecfn count-stones [value n]
  (mod (cond
    (= n 0) 1
    (= value 0) (count-stones 1 (dec n))
    (even-digits? value)
      (let [[a b] (split-digits value)]
        (+ (count-stones a (dec n)) (count-stones b (dec n)))
      )
    :else (count-stones (* value 2024) (dec n))
  ) 1007)
)

(->> (lib/read-pair)
    (map #(count-stones % 400))
    lib/sum
    println
    time
)
