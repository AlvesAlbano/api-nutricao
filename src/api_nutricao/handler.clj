(ns api-nutricao.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [api-nutricao.exercicios.exercicio-api :as exercicio-api]
            [api-nutricao.exercicios.nutricao-api :as nutricao-api]))

(defroutes app-routes
           (GET "/exercicio/:nome-exercicio" [nome-exercicio]
             (exercicio-api/buscar-exercicio nome-exercicio))

           (GET "/alimento/:nome" [nome]
             (nutricao-api/buscar-alimento-api nome))

           (route/not-found {:status 404
                             :headers {"Content-Type" "application/json"}
                             :body {:erro "Rota não encontrada"}}))

(def app
  (wrap-json-response
    (wrap-defaults app-routes site-defaults)))
