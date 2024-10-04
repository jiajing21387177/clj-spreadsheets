(ns components.header
  (:require [reagent.core :as r]
            ["flowbite-react" :refer [Avatar Dropdown Navbar]]
            [components.sidebar :refer [toggle-sidebar]]))

(defn header 
  [{:keys [brand-logo]}]
  [:> Navbar {:fluid true :rounded true :class "fixed top-0 left-0 w-screen z-10"}
   [:> Navbar.Brand {:href "/"}
    [:img.mr-3.h-6.sm:h-9 {:src brand-logo :alt "Flowbite React Logo"}]
    [:span.self-center.whitespace-nowrap.text-xl.font-semibold.dark:text-white "Flowbite React"]]
   [:div.flex.md:order-2
    [:> Dropdown {:arrowIcon false
                  :inline true
                  :label (r/as-element
                          [:> Avatar {:alt "User settings"
                                      :img "https://flowbite.com/docs/images/people/profile-picture-5.jpg"
                                      :rounded true}])}
     [:> Dropdown.Header
      [:span.block.text-sm "Bonnie Green"]
      [:span.block.truncate.text-sm.font-medium "name@flowbite.com"]]
     [:> Dropdown.Item "Dashboard"]
     [:> Dropdown.Item "Settings"]
     [:> Dropdown.Item "Earnings"]
     [:> Dropdown.Divider]
     [:> Dropdown.Item "Sign out"]]
    [:> Navbar.Toggle {:on-click toggle-sidebar}]]
   #_[:> Navbar.Collapse
    [:> Navbar.Link {:href "#" :active true} "Home"]
    [:> Navbar.Link {:href "#"} "About"]
    [:> Navbar.Link {:href "#"} "Services"]
    [:> Navbar.Link {:href "#"} "Pricing"]
    [:> Navbar.Link {:href "#"} "Contact"]]])