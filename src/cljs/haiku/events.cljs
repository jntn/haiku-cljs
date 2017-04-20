(ns haiku.events
    (:require [re-frame.core :as rf]
              [haiku.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(defn type-handler
  "React to typed key and return new state"
  [db [_ key]]
  (-> db
      (let [letter (nth :verse (nth (:haikus db) (:haiku-index db)) (:position db))]
           (if (= letter key)
             (update :position inc)
             (assoc :start-time (.getTime (js/Date.)))))))

(defn current-letter
  "Get current letter ")

(rf/reg-event-db
 :type
 type-handler)
