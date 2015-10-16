(ns numerics.core
  (:import [org.apache.commons.math3.linear RealMatrix Array2DRowRealMatrix
            DecompositionSolver LUDecomposition RealVector ArrayRealVector])
  (:require [clojure.core.matrix :as cm]
            [clojure.core.matrix.operators :refer :all]))

(cm/set-current-implementation :vectorz)

(defn acm-linear-solve
  "Solves aX = b linear systems wrapping some functionality from
  apache-commons-math3."
  [coeficients constants]
  (-> (map double-array coeficients)
      into-array
      Array2DRowRealMatrix.
      LUDecomposition.
      .getSolver
      (.solve (-> constants
                  double-array
                  (ArrayRealVector. false)))
      .toArray
      vec))

(defn underd-pseudo-inverse
  "Suppose y = M*x and assume n<m.
  In this case there are fewer constraints than unknowns, and the system is underdetermined, with an infinite number of solutions.
  We can pick one of these solutions by finding the smallest one. That is, we will minimize x subject to the constraint y = M*x. The method of Lagrange multipliers has us add a term to the quantity to be minimized:
  ||x||^2 + lambda^T * (y − M*x)
  Differentiting w.r.t x and setting the result equal to zero yields
  2*x − M^T * lambda = 0
  We can’t just solve for lambda since M is not a square matrix, but we can premultiply by M to obtain:
  2*M*x − M*M^T*lambda = 0 and using:
  y = Mx gives us:
  2*y = M*M^T*lambda
  so
  lambda = 2 * (M*M^T)^-1 * y, and hence:
  x = M^T * (M*M^T)^-1 * y
  where M^T * (M*M^T) (a m by n matrix) is called a pseudo-inverse."
  [coeficients constraints]
  (-> coeficients
      cm/transpose
      (cm/mmul (->> coeficients
                    cm/transpose
                    (cm/mmul coeficients)
                    cm/inverse)
               constraints)))

(defn overd-pseudo-inverse
  "Suppose y = M*x and assume n>m.
  In this case there are more constraints than unknowns, and the system is overdetermined, with no solutions (except for degenerate cases).
  We can find a least-squares solution that minimizes the error (y − M*x). We want to find x that minimizes:
  ||y − M*x||^2 , or:
  (y − M*x)^T * (y − M*x), or:
  y^T * y − y^T * M*x − x^T * M^T * y + x^T * M^T * M*x
  Differentiating w.r.t. x and setting the result equal to zero yields:
  −(y^T * M )^T − (M^T * y) + 2*M^T * M*x = 0, so:
  x = (M^T * M)^−1 * M^T * y
  where (M^T * M)^−1 * M^T (a m by n matrix) is called a pseudo-inverse."
  [coeficients constants]
  (cm/mmul (-> coeficients
               cm/transpose
               (cm/mmul coeficients)
               cm/inverse)
           (cm/transpose coeficients)
           constants))
