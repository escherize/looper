(ns looper.views
  (:require [reagent.core :as r]
            [cljs.reader :as reader]
            [re-frame.core :as re-frame :refer [dispatch subscribe]]
            [looper.js-inits :as jsi]))

(defn edit-panel-content []
  (let [graph-data (subscribe [:graph-data])]
    (fn []
      [:div
       [:input {:type :button
                :value "db?"
                :on-click (fn [] (dispatch [:db]))}]
       [:code (pr-str @graph-data)]
       [:div#editor "[A B]"]])))

(defn safe-read [s on-success]
  (try (let [data (reader/read-string s)]
         (on-success data))
       (catch :default e nil)))

(defn handle-ace-update [& [e]]
  (let [raw-ace-string (.getValue (.edit js/ace "editor"))
        ace-string (str "[" raw-ace-string "]")]
    (safe-read ace-string
               #(dispatch [:graph-data %]))))



(defn edit-panel []
  (r/create-class {:render edit-panel-content
                   :component-did-mount
                   (fn []
                     (jsi/init-ace handle-ace-update)
                     (jsi/init-cola))}))

(defn graph-panel []
  [:div#graph "ok"])

(defn main-panel []
  [:div
   [edit-panel]
   [graph-panel]])
