(ns haiku.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]
            [clojure.string :as str]))

(defn verse [db]
  (let [index (:haiku-index db)]
    (:verse (nth (:haikus db) index))))

(rf/reg-sub :verse verse)

(defn verse-as-list [verse query-v _]
  (seq verse))

(rf/reg-sub
  :verse-as-list
  :<- [:verse]
  verse-as-list)