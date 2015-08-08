(ns looper.views
  (:require [reagent.core :as r]
            [cljs.reader :as reader]
            [re-frame.core :as re-frame :refer [dispatch subscribe]]
            [looper.js-inits :as jsi]))

(defn edit-panel-content []
  (let [parsed-data (subscribe [:parsed-data])
        graph-data (subscribe [:graph-data])]
    (fn []
      [:div
       [:h1 "working title"]
       [:span "Make the graph in the thing. pfft."]

       [:div#editor {:style {:position "fixed"
                             :min-height "400px"
                             :width "400px"
                             :margin "15px"
                             :border "rgba(255, 255, 255, 0.5) 1px solid"
                             ;:border-radius "5px"
                             :background-color "rgba(0, 0, 0, 0.0)"
                             }}
        "[4 5]
[6 4 3 2 5 1 2]"]])))

(defn safe-read [s on-success]
  (try (let [data (reader/read-string s)]
         (on-success data))
       (catch :default e nil)))

(defn handle-ace-update [& [e]]
  (let [raw-ace-string (.getValue (.edit js/ace "editor"))
        ace-string (str "[" raw-ace-string "]")]
    (safe-read ace-string
               #(dispatch [:parsed-data %]))))

(defn edit-panel []
  (r/create-class {:render edit-panel-content
                   :component-did-mount
                   (fn []
                     (jsi/init-ace handle-ace-update))}))

(defn main-panel []
  [edit-panel])
