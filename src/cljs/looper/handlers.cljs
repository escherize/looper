(ns looper.handlers
  (:require [re-frame.core :as re-frame]
            [looper.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/register-handler
 :db
 (fn [db _]
   (.log js/console (pr-str db))
   db))

(re-frame/register-handler
 :content
 (fn [db [_ content]]
   (assoc db :content content)))

(re-frame/register-handler
 :graph-data
 (fn [db [_ graph-data]]
   (assoc db :graph-data graph-data)))
