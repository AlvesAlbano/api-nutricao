(ns api-nutricao.exercicios.exercicio-repository
  (:require [api-nutricao.data.listas :as data]
            [api-nutricao.exercicios.resource.api :as api-exercicio]
            [api-nutricao.traducao.traduzir-api :as traduzir]
            [cheshire.core :as json]
            [clj-http.client :as http]
            )
  )

(defn formatar-corpo [corpo]
  {:nome (traduzir/ingles-portugues (:name corpo))
   :calorias-por-hora (:calories_per_hour corpo)
   :duracao-minutos (:duration_minutes corpo)
   :total-calorias (:total_calories corpo)}
  )

(defn buscar-exercicios
  ([nome-exercicio]
  (let [resposta (http/get api-exercicio/URL {:headers
                                              {:X-Api-Key api-exercicio/KEY}
                                              :query-params {:activity nome-exercicio}})]
    (:body resposta)
    )
   )

  ([nome-exercicio peso duracao]
  (let [resposta (http/get api-exercicio/URL {:headers
                                              {:X-Api-Key api-exercicio/KEY}
                                              :query-params {:activity nome-exercicio
                                                             :weight peso
                                                             :duration duracao}})
    corpo (json/parse-string (:body resposta) true)
    corpo-formatado (mapv formatar-corpo corpo)]

    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string corpo-formatado)}
    )
  )
  )

(defn listar-perdas []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @data/lista-perda)}
  )