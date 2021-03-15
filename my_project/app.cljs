(ns my-project.app
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]
    [cljs.reader :as readder]))


(def current-number (r/atom "0"))
(def previous-number (r/atom "0"))
(def operator (r/atom "nil"))
(def contains-new-value (r/atom true))

(defn reset []
      (reset! current-number "0")
      (reset! previous-number "0")
      (reset! operator "nil")
      (reset! contains-new-value true)
      )

(defn set-number [num]
      (if (= true @contains-new-value)
        (do
          (if
            (= "0" @current-number)
            (do
              (reset! current-number num)
              (js/console.log (str "current number 1-> " @current-number)))
            (do
              (reset! current-number (str @current-number num))
              (js/console.log (str "current number2 -> " @current-number)))
            )
          )
        (do
          (reset! previous-number @current-number)
          (reset! current-number num)
          (reset! contains-new-value true)
          (js/console.log (str "new number-> " @current-number))
          )
        )
      )

(defn map-to-func [opr]
      (cond
        (= opr "+") +
        (= opr "-") -
        (= opr "*") *
        (= opr "/") /
        )
      )

(defn add-operator [opr]
      (reset! operator opr)
      (reset! contains-new-value false)

      (if (not= "0" @previous-number)
        (calculate)
        )

      (js/console.log (str "current number 1.1 " @current-number))
      (js/console.log (str "previous no 1.1 " @previous-number))
      (js/console.log (str "operator 1.1 " @operator))
      )


(defn calculate []
      (reset! current-number ((map-to-func @operator) (js/parseInt @previous-number) (js/parseInt @current-number)))
      (js/console.log (str "operator 1.2 " @operator))
      (js/console.log (str "current number 1.2 " @current-number))
      (js/console.log (str "previous no 1.2" @previous-number))
      )

(defn mini-app []
      [:table {:border "1"}
       [:tbody
        [:tr
         [:td {:colspan "3"} [:input#result {:readonly "" :type "text" :value @current-number}]]
         [:td [:input {:type "button" :value "c" :on-click #(reset)}]]]
        [:tr
         [:td [:input {:type "button" :value "1" :on-click #(set-number "1")}]]
         [:td [:input {:type "button" :value "2" :on-click #(set-number "2")}]]
         [:td [:input {:type "button" :value "3" :on-click #(set-number "3")}]]
         [:td [:input {:type "button" :value "/" :on-click #(add-operator "/")}]]]
        [:tr
         [:td [:input {:type "button" :value "4" :on-click #(set-number "4")}]]
         [:td [:input {:type "button" :value "5" :on-click #(set-number "5")}]]
         [:td [:input {:type "button" :value "6" :on-click #(set-number "6")}]]
         [:td [:input {:type "button" :value "-" :on-click #(add-operator "-")}]]]
        [:tr
         [:td [:input {:type "button" :value "7" :on-click #(set-number "7")}]]
         [:td [:input {:type "button" :value "8" :on-click #(set-number "8")}]]
         [:td [:input {:type "button" :value "9" :on-click #(set-number "9")}]]
         [:td [:input {:type "button" :value "+" :on-click #(add-operator "+")}]]]
        [:tr
         [:td [:input {:type "button" :value "."}]]
         [:td [:input {:type "button" :value "0"}]]
         [:td [:input {:type "button" :value "=" :on-click #(calculate)}]]
         [:td [:input {:type "button" :value "*" :on-click #(add-operator "*")}]]]]]
      )

(defn ^:export run []
      (rdom/render [mini-app] (js/document.getElementById "app")))

(defn ^:export reload []
      (.log js/console "reload...")
      (run))