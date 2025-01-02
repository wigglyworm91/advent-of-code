(ns day4.part1
  (:require [lib.lib :as lib])
  (:require [clojure.string :as str])
)

(defn count-xmas [txt]
  (+
   (count (re-seq #"XMAS" txt))
   (count (re-seq #"SAMX" txt))
  )
)

(defn num-diags [lines]
  (- (+ (count lines) (count (first lines))) 1)
)

(assert (= (num-diags ["ab" "cd"]) 3))

(defn str-transpose [lines]
  (->> lines
       (map vec)
       (apply mapv vector)
       (map (partial apply str))
  )
)

(defn diag-transpose [lines]
  (for [i (range (num-diags lines))]
    (str/join (filter identity (for [j (range (+ 1 i))]
      ;; (i,j) -> (row,col)
      ;; (2,0) -> (2,0)
      ;; (2,1) -> (1,1)
      ;; (2,2) -> (0,2)
      (let [row (- i j) col j]
        (if (and (< row (count lines)) (< col (count (first lines))))
          (nth (nth lines (- i j)) j)
          nil
        )
      )
    )))
  )
)

(defn diag-transpose* [lines]
  (diag-transpose (map reverse lines))
)

(let [lines (lib/read-all-lines)]
  ;;(println (str/join "\n" lines))
  ;;(println lines)
  ;;(println (str-transpose lines))
  ;;(println (diag-transpose lines))
  ;;(println (diag-transpose* lines))
  (println (reduce + (map count-xmas (flatten [
    lines
    (str-transpose lines)
    (diag-transpose lines)
    (diag-transpose* lines)
  ]))))
)
