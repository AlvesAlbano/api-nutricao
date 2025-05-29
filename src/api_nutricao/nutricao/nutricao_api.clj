(ns api-nutricao.nutricao.nutricao-api
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [api-nutricao.traducao.traduzir-api :as trad]))

(def api-key "wEs6WCnpgqM3QnNWYwQKBqzjch59uWB7Nye9VT0Q")

(defn buscar-alimento-api [nome-pt]
  (let [nome-en (trad/portugues-ingles nome-pt)
        url "https://api.nal.usda.gov/fdc/v1/foods/search"
        response (client/get url {:query-params {"api_key" api-key
                                                 "query" nome-en}
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
