(ns lib.surd
)

;; Helper function to simplify radicands
(defn square-free? [n]
  (let [sqrt (Math/sqrt n)]
    (= (Math/round sqrt) sqrt)
  )
)

(defn normalize-sqrt [coefficient radicand]
  (let [sqrt-radicand (int (Math/sqrt radicand))]
    (if (square-free? radicand)
      (->SqrtExpr coefficient radicand)
      ;; if radicand is not square-free, factor out the square part
      (let [
          new-coeff (* coefficient sqrt-radicand)
          new-radicand (/ radicand (* sqrt-radicand sqrt-radicand))
        ]
        (->SqrtExpr new-coeff new-radicand)
      )
    )
  )
)

(defrecord SqrtExpr [coefficient radicand])

(defn :+ [s1 s2]
  (let [
      [c1 r1] (:coefficient s1) (:radicand s1)
      [c2 r2] (:coefficient s2) (:radicand s2)
    ]
    (if (= r1 r2)
      (->SqrtExpr (+ c1 c2) r1)
      (throw (ex-info "Cannot add square roots with different radicands"
        {:s1 s1 :s2 s2}
      ))
    )
  )
)
