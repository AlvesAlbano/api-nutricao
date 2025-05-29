(ns api-nutricao.core
     (:require [api-nutricao.handler :refer [app]]
               [ring.adapter.jetty :refer [run-jetty]])
     (:gen-class))

(defn -main [& args]
  (run-jetty app {:port 3000 :join? false})
  (println "http://localhost:3000/exercicio/skiing")
  )