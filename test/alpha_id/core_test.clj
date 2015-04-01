(ns alpha-id.core-test
  (:require [clojure.test :refer :all]
            [alpha-id.core :refer :all]))

(deftest a-test
  (testing "ID generation"
    (let [num 9007199254740992]
      (is (= num (decode dict-16 (encode dict-16 num))))
      (is (= num (decode dict-32 (encode dict-32 num))))
      (is (= num (decode dict-64 (encode dict-64 num))))
      (is (= num (decode dict-89 (encode dict-89 num)))))))
