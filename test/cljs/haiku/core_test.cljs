(ns haiku.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [haiku.core :as core]
            [haiku.db :as db]
            [haiku.events :as e]
            [cljs.spec :as s]))

(def last-letter
  (-> db/default-db
      (assoc :position 40)))

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
    (let [result (e/type-handler last-letter [:key "."])]
      (is (= (:state result) :contine)))))

