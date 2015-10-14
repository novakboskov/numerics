(ns numerics.core
  (:import [org.apache.commons.math3.linear RealMatrix Array2DRowRealMatrix
            DecompositionSolver LUDecomposition RealVector ArrayRealVector]))

(defn linear-solve
  "Solves aX = b linear systems wrapping same functionality from
  commons-math3."
  [coeficients constants]
  (-> (map double-array coeficients)
      (into-array)
      (Array2DRowRealMatrix.)
      (LUDecomposition.)
      (.getSolver)
      (.solve (-> constants
                  double-array
                  (ArrayRealVector. false)))
      (.toArray)
      (vec)))
