(ns looper.views
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]))

(defn edit-panel-content []
  (let [content (re-frame/subscribe [:content])]
    (fn []
      [:div
       [:div "Here's some " @content]
       [:div#editor "(defn square [x]
  \"Squares a number.\"
  (* x x))"]])))

(defn edit-panel []
  (r/create-class {:render edit-panel-content
                   :component-did-mount
                   (fn [] (let [editor (.edit js/ace "editor")]
                            (.. editor
                                (setTheme "ace/theme/monokai"))
                            (.setMode (.getSession editor)
                                      "ace/mode/clojurescript")
                            (.setOptions editor
                                         #js {:enableBasicAutocompletion true
                                              :enableSnippets true
                                              :showGutter false
                                              :highlightActiveLine false
                                              :showPrintMargin false
                                              :fontSize 30})))}))

(defn main-panel []
  [:div

   [edit-panel]])
