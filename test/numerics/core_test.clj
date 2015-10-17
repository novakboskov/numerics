(ns numerics.core-test
  (:require [clojure.test :refer :all]
            [numerics.core :refer :all]))

(deftest acm-linear-solve-test
  (testing "acm-linear-solve testing."
    ;; 100g eggs
    (is (=
         (acm-linear-solve [[0.499 0.0006 -0.3] [0.085 0.0085 -0.4] [0.039 0.8111 -0.3]]
                       [-0.0077 -0.1258 -0.0994])
         [0.1990789632937021 -1.5259331881189165E-4 0.3568010370918869]))
    ;; 100g bread
    (is (=
         (acm-linear-solve [[0.0077 0.0006 -0.3] [0.1258 0.0085 -0.4] [0.0994 0.8111 -0.3]]
                       [-0.499 -0.08 -0.03])
         [5.065993120613653 0.005488501961416609 1.7933714670996734]))))

(deftest underd-pseudo-inverse-test
  (testing "underd-pseudo-inverse method testing."
    (is (=
         (underd-pseudo-inverse [[1 1 1] [1 1 2]] [1 3])
         [-0.5 -0.5 2.0]))
    ;; 100g eggs with peas included
    (is (=
         (underd-pseudo-inverse [[0.499 0.0006 0.1445 -0.3] [0.039 0.8111 0.004 -0.3]
                                 [0.085 0.0085 0.0542 -0.4]] [-0.0077 -0.0994 -0.1258])
         [0.19532029406891444 4.5078579368261484E-4 0.015786666292379 0.3581542349703774]))
    ;; 100g bread with n by n problem, underd-pseudo-inverse-test can solve n by n problems
    ;; the results are slightly differ
    (is (=
         (underd-pseudo-inverse [[0.0077 0.0006 -0.3] [0.1258 0.0085 -0.4] [0.0994 0.8111 -0.3]]
                                [-0.499 -0.08 -0.03])
         [5.065993120613627 0.0054885019614173 1.7933714670996652]))))

(deftest overd-pseudo-inverse-test
  (testing "overd-pseudo-inverse testing"
    ;; 100g bread, eggs only
    (is (=
         (overd-pseudo-inverse [[0.0077 -0.3] [0.1258 -0.4] [0.0994 -0.3]]
                               [-0.499 -0.085 -0.039])
         [5.0202342161418 1.7921126797673275]))))
