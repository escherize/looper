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
       [:input {:type :button :value "db?" :on-click (fn [] (dispatch [:db]))}]
       ;;[:code (pr-str @parsed-data)]
       [:span [:code (pr-str @graph-data)]]
       [:div#editor {:style {:height "200px"}}
        "[4 5] [6 4 3 2 5 1 2]"]])))

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
