(ns api-nutricao.core
  (:require [api-nutricao.handler :refer [app]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println (str "Iniciando servidor na porta " port))
    (run-jetty app {:port port :join? false})
    )
  )