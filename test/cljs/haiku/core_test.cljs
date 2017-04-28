(ns haiku.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [haiku.core :as core]
            [haiku.db :as db]
            [haiku.events :as e]
            [cljs.spec :as s]))

(def at-last-letter
  (-> db/default-db
      (assoc :position 37)))

(deftest spec-test
  (testing "Default db"
    (println (s/explain-str :haiku.db/db db/default-db))
    (is (= (s/valid? :haiku.db/db db/default-db) true) "Db is valid accoding to spec")))

(deftest typed-keys
  (testing "Entering a correct key"
    (let [result (e/type-handler db/default-db [:key "T"])]
      (is (= (:position result) 1) "Position advances")
      (is (int? (:start-time result)) "Time is started")))

  (testing "Entering incorrect key"
    (let [result (e/type-handler db/default-db [:key "A"])]
      (is (= (:state result) :error) "Haiku is in error state")))

  (testing "Entering the last key"
    (let [result (e/type-handler at-last-letter [:key "."])]
      (is (= (:state result) :continue)))))

(deftest helpers
  (testing "Getting current letter from db"
    (let [key (haiku.events/get-current-letter db/default-db)]
      (is (= key "T"))))
  (testing "Checking if we are at first letter when we are"
    (let [is-last-letter (haiku.events/is-first-letter db/default-db)]
      (is (= is-last-letter true))))
  (testing "Checking if we are at first letter when we are not"
    (let [is-last-letter (haiku.events/is-first-letter at-last-letter)]
      (is (= is-last-letter false))))
  (testing "Checking if we are at last letter when we are not"
    (let [is-last-letter (haiku.events/is-last-letter db/default-db)]
      (is (= is-last-letter false))))
  (testing "Checking if we are at last letter when we are"
    (let [is-last-letter (haiku.events/is-last-letter at-last-letter)]
      (is (= is-last-letter true)))))