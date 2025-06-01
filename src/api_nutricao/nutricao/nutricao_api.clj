(ns api-nutricao.nutricao.nutricao-api
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [api-nutricao.traducao.traduzir-api :as trad]
            [clojure.string :as str]))

(def api-key "wEs6WCnpgqM3QnNWYwQKBqzjch59uWB7Nye9VT0Q")

(defn montar-descricao [alimento]
  (let [desc (:description alimento)
        categoria (:foodCategory alimento)
        marca (:brandOwner alimento)
        tipo (:dataType alimento)]
    (->> [desc categoria marca tipo]
         (remove nil?)
         (str/join " - "))))

(defn buscar-alimento-api [nome-pt]
  (let [nome-en (trad/portugues-ingles nome-pt)
        url "https://api.nal.usda.gov/fdc/v1/foods/search"
        response (client/get url {:query-params {"api_key" api-key
                                                 "query" nome-en}
                                  :headers {"Accept" "application/json"}
                                  :as :json})
        alimentos (get-in response [:body :foods])
        alimentos-processados (mapv #(assoc % :descricao-detalhada (montar-descricao %))
                                    alimentos)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body alimentos-processados}))

(def refeicoes-dia (atom []))

(defn adicionar-refeicao [refeicao]
  (swap! refeicoes-dia conj refeicao))

(defn listar-refeicoes []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @refeicoes-dia)})
