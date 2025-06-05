(ns api-nutricao.nutricao.nutricao-api
  (:require [api-nutricao.traducao.traduzir-api :as trad]
            [cheshire.core :as json]
            [clj-http.client :as client]
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

(def refeicoes-dia (atom {:refeicoes [] :calorias-totais 0}))

(defn adicionar-refeicao [refeicao]
  (swap! refeicoes-dia
         (fn [{:keys [refeicoes calorias-totais]}]
           {:refeicoes (conj refeicoes refeicao)
            :calorias-totais (+ calorias-totais (:calorias refeicao))})))

(defn listar-refeicoes []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @refeicoes-dia)})