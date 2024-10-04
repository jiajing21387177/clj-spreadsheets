(ns components.sidebar
  (:require
    ["flowbite-react" :refer [Drawer Sidebar]]
    ["react-icons/hi" :refer [HiArrowSmRight HiChartPie HiInbox HiShoppingBag HiTable HiUser]]
    [reagent.core :as r]))

(defonce open? (r/atom false))

(defn toggle-sidebar
  []
  (swap! open? not))

(defn sidebar-mobile
  []
  [:div {:class "md:hidden"}
   [:> Drawer {:open @open?
               :onClose toggle-sidebar}
    [:> Drawer.Header {:title "Menu"}]
    [:> Drawer.Items
     [:> Sidebar {:aria-label "Sidebar with multi-level dropdown example"
                  :class "[&>div]:bg-transparent [&>div]:p-0"}
      [:> Sidebar.Items
       [:> Sidebar.ItemGroup
        [:> Sidebar.Item {:href "#" :icon HiChartPie} "Dashboard"]
        [:> Sidebar.Collapse {:icon HiShoppingBag :label "E-commerce"}
         [:> Sidebar.Item {:href "#"} "Products"]
         [:> Sidebar.Item {:href "#"} "Sales"]
         [:> Sidebar.Item {:href "#"} "Refunds"]
         [:> Sidebar.Item {:href "#"} "Shipping"]]
        [:> Sidebar.Item {:href "#" :icon HiInbox} "Inbox"]
        [:> Sidebar.Item {:href "#" :icon HiUser} "Users"]
        [:> Sidebar.Item {:href "#" :icon HiShoppingBag} "Products"]
        [:> Sidebar.Item {:href "#" :icon HiArrowSmRight} "Sign In"]
        [:> Sidebar.Item {:href "#" :icon HiTable} "Sign Up"]]]]]]])

(defn sidebar
  []
  [:> Sidebar {:aria-label "Sidebar with multi-level dropdown example"
               :class "hidden md:block fixed left-0 top-0 h-[calc(100vh-60px)] pt-[60px] border-r"}
   [:> Sidebar.Items
    [:> Sidebar.ItemGroup
     [:> Sidebar.Item {:href "#" :icon HiChartPie} "Dashboard"]
     [:> Sidebar.Collapse {:icon HiShoppingBag :label "E-commerce"}
      [:> Sidebar.Item {:href "#"} "Products"]
      [:> Sidebar.Item {:href "#"} "Sales"]
      [:> Sidebar.Item {:href "#"} "Refunds"]
      [:> Sidebar.Item {:href "#"} "Shipping"]]
     [:> Sidebar.Item {:href "#" :icon HiInbox} "Inbox"]
     [:> Sidebar.Item {:href "#" :icon HiUser} "Users"]
     [:> Sidebar.Item {:href "#" :icon HiShoppingBag} "Products"]
     [:> Sidebar.Item {:href "#" :icon HiArrowSmRight} "Sign In"]
     [:> Sidebar.Item {:href "#" :icon HiTable} "Sign Up"]]]])