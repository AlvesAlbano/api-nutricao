(ns api-nutricao.exercicios.exercicio-repository
  (:require [api-nutricao.data.listas :as data]
            [api-nutricao.exercicios.resource.api :as api-exercicio]
            [api-nutricao.exercicios.util.conversor :as conversor]
            [cheshire.core :as json]
            [clj-http.client :as http]
            )
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
                                                             :weight (conversor/kilo-libras peso)
                                                             :duration duracao}})]
    (:body resposta)
    )
  )
  )

(defn listar-perdas []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @data/lista-perda)}
  )