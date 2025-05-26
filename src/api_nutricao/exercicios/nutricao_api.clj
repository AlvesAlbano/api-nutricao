(ns api-nutricao.exercicios.nutricao-api
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(def api-key "wEs6WCnpgqM3QnNWYwQKBqzjch59uWB7Nye9VT0Q")

(defn buscar-alimento-api [nome]
  (let [url "https://api.nal.usda.gov/fdc/v1/foods/search"
        response (client/get url {:query-params {"api_key" api-key
                                                 "query" nome}
                                  :headers {"Accept" "application/json"}
                                  :as :json})
        alimentos (get-in response [:body :foods])]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body alimentos}))

(def refeicoes-dia (atom []))

(defn adicionar-refeicao [refeicao]
  (swap! refeicoes-dia conj refeicao))

(defn listar-refeicoes []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @refeicoes-dia)})
