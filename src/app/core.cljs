(ns app.core
  (:require ["react-dom/client" :refer [createRoot]]
            ["@instantdb/react" :as instantdb :refer [tx]]
            [reagent.core :as r]
            [components.header :refer [header]]
            [components.sidebar :refer [sidebar-mobile sidebar]]))

(defonce instantdb-app-id "e8ddc488-c16d-41bd-b4ce-efb7cdf9f179")

(def db
  (instantdb/init {:appId instantdb-app-id}))

(defonce root (createRoot (.getElementById js/document "app")))

(defonce todos (r/atom []))

;; Write Data
(defn add-todo [title]
  (let [todo-id (instantdb/id)  ;; Generate a unique id
        created-at (.now js/Date)  ;; Current timestamp
        todo-data #js {:title title
                       :done false
                       :created-at created-at}
        resp (.transact db
                        (.update (aget (.-todos tx) todo-id) todo-data))]  ;; Todo data
    ;; Perform the transaction
    (-> resp
        (.then (fn [result]
                 (js/console.log "result: " result)))
        (.catch (fn [error]
                  (js/console.error error))))))

(comment 
  (add-todo "Learn InstantDB"))

(defn delete-todo [todo]
  (.transact db (-> (instantdb/tx.todos (.-id todo)) (.delete))))

(defn toggle-done [todo]
  (.transact db (-> (instantdb/tx.todos (.-id todo)) (.update #js {:done (not (.-done todo))}))))

(defn main [{:keys [body]}]
  [:div.bg-gray-50
   [header {:brand-logo "https://placehold.co/600x400"}]
   [sidebar]
   [sidebar-mobile]
   [:main {:class "pt-[60px] md:pl-[255px]"}
    body]])

(defn todo-item [{:keys [id index title done] :as todo}]
  [:li.input-group.checkbox
   [:input {:id id
            :type "checkbox"
            :checked done
            :on-change #(swap! todos update-in [index :done] not)}]
   [:label
    {:for id
     :class [(when done "line-through")]}
    title]
   [:button.btn.btn-danger.ml-4
    {:on-click #(delete-todo todo)}
    "Delete"]])

(defn todo-form []
  (let [new-todo (r/atom "")]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (when (seq @new-todo)
                             (add-todo @new-todo)
                             (reset! new-todo "")))}
       [:div.input-group
        [:input {:type "text"
                 :value @new-todo
                 :on-change #(reset! new-todo (.. % -target -value))
                 :placeholder "Add a new todo"}]]
       [:button.btn.btn-primary {:type "submit"} "Add"]])))

(defn todo-list-inner []
  (let [{:keys [isLoading error data]} (.useQuery db #js {:todos {}})]
    (js/console.log data)
    (if isLoading
      [:p "Loading..."]
      (if error
        [:p (str "Error: " error)]
        (do
          (println data)
          [:ul
           (for [[index todo] (map-indexed vector @todos)]
             ^{:key (:id todo)} [todo-item (merge todo {:index index})])])))))

(defn todo-list []
  [:f> main {:body [:section.p-4
                    [:h1 "Todo List"]
                    [todo-form]
                    [:f> todo-list-inner]]}])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start")
  (.render root (r/as-element [todo-list])))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
