(ns api-nutricao.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [api-nutricao.exercicios.exercicio-api :as exercicio-api])
  )

(defroutes app-routes
  (GET "/exercicio/:nome-exercicio" [nome-exercicio] (exercicio-api/buscar-exercicio nome-exercicio))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))