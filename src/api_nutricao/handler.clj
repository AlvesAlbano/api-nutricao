(ns api-nutricao.handler
  (:require [api-nutricao.exercicios.exercicio-controller :as exercicio-api]
            [api-nutricao.exercicios.exercicio-repository :as exercicio-repository]
            [api-nutricao.exercicios.util.conversor :as conversor]
            [api-nutricao.nutricao.nutricao-api :as nutricao-api]
            [api-nutricao.traducao.traduzir-api :as traduzir]
            [api-nutricao.usuario.usuario-controller :as usuario-controller]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defroutes app-routes
           (GET "/exercicio" [activity weight duration]
             (let [nome-exercicio (traduzir/portugues-ingles activity)
                   peso (conversor/kilo-libras (Double/parseDouble weight))
                   duracao (Double/parseDouble duration)]
               (exercicio-api/buscar-exercicio nome-exercicio peso duracao)))

           (GET "/calorias-perdidas" []
             (exercicio-repository/listar-perdas))

           (GET "/usuario" []
             (usuario-controller/get-usuario))

           (POST "/registrar-perda" request
             (let [exercicio (:body request)]
               (exercicio-api/registrar-perda exercicio)
               {:status 201
                :headers {"Content-Type" "application/json"}
                :body {:mensagem "Perda calórica adicionada com sucesso!"}}))

           (POST "/cadastrar-usuario" request
             (let [usuario (:body request)]
               (usuario-controller/cadastrar-usuario usuario)
               {:status 201
                :headers {"Content-Type" "application/json"}
                :body {:mensagem "Usuário cadastrado com sucesso!"}}))

           (GET "/alimento/:nome" [nome]
             (do
               (nutricao-api/buscar-alimento-api nome)))

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