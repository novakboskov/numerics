(ns numerics.core-test
  (:require [clojure.test :refer :all]
            [numerics.core :refer :all]))

(deftest linear-solve-test
  (testing "linear-solve testing."
    ;; 100g eggs
    (is (= (linear-solve [[0.499 0.0006 -0.3] [0.085 0.0085 -0.4] [0.039 0.8111 -0.3]] [-0.0077 -0.1258 -0.0994])
           [0.1990789632937021 -1.5259331881189165E-4 0.3568010370918869]))
    ;; 100g bread
    (is (= (linear-solve [[0.0077 0.0006 -0.3] [0.1258 0.0085 -0.4] [0.0994 0.8111 -0.3]] [-0.499 -0.08 -0.03])
           [5.065993120613653 0.005488501961416609 1.7933714670996734]))))
