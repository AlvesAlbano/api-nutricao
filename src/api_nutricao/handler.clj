(ns api-nutricao.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [api-nutricao.exercicios.exercicio-api :as exercicio-api]
            [api-nutricao.exercicios.nutricao-api :as nutricao-api]
            [cheshire.core :as json]))

(defroutes app-routes
           (GET "/exercicio/:nome-exercicio" [nome-exercicio]
             (exercicio-api/buscar-exercicio nome-exercicio))

           (GET "/alimento/:nome" [nome]
             (nutricao-api/buscar-alimento-api nome))

           (GET "/refeicoes" []
             (nutricao-api/listar-refeicoes))

           (POST "/refeicoes" request
             (let [refeicao (:body request)]
               (nutricao-api/adicionar-refeicao refeicao)
               {:status 201
                :headers {"Content-Type" "application/json"}
                :body {:mensagem "Refeição adicionada com sucesso!"}}))

           (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
