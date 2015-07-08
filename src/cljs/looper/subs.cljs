(ns looper.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :content
 (fn [db]
   (reaction (:content @db))))


(re-frame/register-sub
 :graph-data
 (fn [db]
   (reaction (:graph-data @db))))
