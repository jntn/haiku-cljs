(ns haiku.views
  (:require [re-frame.core :as re-frame :refer [subscribe]]))

(defn haiku []
  (fn []
     [:div
      (for [c @(subscribe [:verse-as-list])]
        (if (= c \newline)
          [:br]
          [:span c]))]))

(defn main-panel []
  (fn []
    [:div [haiku]]))