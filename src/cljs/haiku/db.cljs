(ns haiku.db
    (:require [re-frame.core :as rf]
              [cljs.spec :as s]))

(s/def ::position int?)
(s/def ::haiku-index int?)
(s/def ::state #{:default :error :continue})
(s/def ::start-time (s/nilable int?))
(s/def ::verse string?)
(s/def ::author string?)
(s/def ::haiku (s/keys :req-un [::verse ::author]))
(s/def ::haikus (s/coll-of ::haiku))
(s/def ::db (s/keys :req-un [::position ::state ::haiku-index ::start-time]))

(def default-db
  {:haikus
   [{:verse "The wren\nEarns his living\nNoiselessly."
     :author "Kobayashi Issa"}
    {:verse "Over the wintry\rforest, winds howl in rage\rwith no leaves to blow."
     :author "Natsume Sōseki"}]
   :haiku-index 0
   :position 0
   :state :default
   :start-time nil})
