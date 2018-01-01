(ns alpha-id.core-test
  (:require [clojure.test :refer :all]
            [alpha-id.core :refer :all]))

(deftest a-test
  (testing "ID generation"
    (let [num 9007199254740992]
      (is (= num (decode-16 (encode-16 num))))
      (is (= num (decode-32 (encode-32 num))))
      (is (= num (decode-64 (encode-64 num))))
      (is (= num (decode-89 (encode-89 num)))))))
