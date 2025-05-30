(ns api-nutricao.exercicios.exercicio-repository
  (:require [api-nutricao.exercicios.resource.api :as api-exercicio]
            [api-nutricao.data.listas :as data]
            [cheshire.core :as json]
            [clj-http.client :as http]
            )
  )

(defn buscar-exercicios [nome-exercicio]
  ;(let [resposta (http/get api-exercicio/URL {:headers
  ;                                            {:X-Api-Key api-exercicio/KEY}
  ;                                            :query-params {:activity nome-exercicio}})]
  ;  (:body resposta)
  ;  )

  ;(let [resposta (http/get api-url {:headers
  ;                                  {:X-Api-Key api-key}
  ;                                  :query-params {:activity nome-exercicio}})
  ;      dados (json/parse-string (:body resposta) true)
  ;      nome-traduzido (traduzir/ingles-portugues (:name dados))]
  ;  (assoc dados :name nome-traduzido))
  )

(defn enumerar-exercicios [])

(defn listar-perdas []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @data/lista-perda)}
  )