(ns api-nutricao.exercicios.exercicio-api
  (:require [clj-http.client :as http])
  )

(def api-url "https://api.api-ninjas.com/v1/caloriesburned?activity=")
(def api-key "s5U2Ubl5YwolmRwIdThZnw==ZcIc4y1MQ4mmFTmT")

(def lista-exercicios (atom []))

(defn buscar-exercicio [nome-exercicio]
  :body (http/get api-url {:headers
                           {:X-Api-Key api-key}
                     :query-params {:activity nome-exercicio}})
  )