(ns haiku.core-test
          (:require [cljs.test :refer-macros [deftest testing is]]
                    [haiku.core :as core]
                    [haiku.db :as db]
                    [haiku.events :as events]
                    [cljs.spec :as s]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 1))))

;;(deftest another-fake
;;  (testing "db"
;;    (is (= (db/default-db :position) 0))))

(deftest spec-test
  (testing "is valid spec"
    ;;(println :haiku.db/db)
    ;;(println db/default-db)
    (println (s/explain-str :haiku.db/db db/default-db))
    (is (= (s/valid? :haiku.db/db db/default-db) true))))

(deftest event-test
  (testing "event handler"
    (let [result (events/type-handler {:position 0 :start-time nil} "A")]
      (println result)
      (is (= (:position result) 1)))))
