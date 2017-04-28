(ns haiku.events
  (:require [re-frame.core :as rf]
            [haiku.db :as db]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(defn get-current-letter
  [db]
  "Gets current letter from db"
  (nth (:verse (nth (:haikus db) (:haiku-index db))) (:position db)))

(defn is-last-letter [db]
  (let [verse (:verse (nth (:haikus db) (:haiku-index db)))
        position (:position db)]
    (= (+ position 1) (count verse))))

(defn is-first-letter [db]
  (let [position (:position db)]
    (= position 0)))

(defn handle-correct-key [db]
  "Handle updates to state when entering correct key"
  (cond
    (is-first-letter db) (-> db
                           (update :position inc)
                           (assoc :start-time (.getTime (js/Date.))))
    (is-last-letter db) (assoc db :state :continue)
    :else (update db :position inc)))

(defn type-handler
  "React to typed key and return new state"
  [db [_ key]]
  (let [letter (get-current-letter db)]
    (if (= letter key)
      (handle-correct-key db)
      (assoc db :state :error))))

(rf/reg-event-db
  :type
  type-handler)
