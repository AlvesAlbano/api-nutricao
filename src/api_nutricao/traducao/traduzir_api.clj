(ns api-nutricao.traducao.traduzir-api

(:require [cheshire.core :as json]
            [clj-http.client :as http]
            [clojure.string :as str]))

(def api-url "https://ftapi.pythonanywhere.com/")

(defn substituir-espacos [frase]
  (str/replace frase #" " "%20"))

(defn retorna-primeiro-elemento [conteudo]
  (let [possiveis (get-in conteudo [:translations :possible-translations])]
    (if (and (vector? possiveis) (seq possiveis))
      (first possiveis)
      ""))) ; retorno seguro em caso de erro ou resposta vazia

(defn portugues-ingles [frase]
  (try
    (let [url-requisicao (str api-url "translate?sl=pt&dl=en&text=" frase)
          resposta (http/get url-requisicao {:accept :json})
          corpo (json/parse-string (:body resposta) true)]
      (or (retorna-primeiro-elemento corpo) frase))
    (catch Exception e
      (println "Erro ao traduzir pt→en:" frase "-" (.getMessage e))
      frase)))

(defn ingles-portugues [frase]
  (try
    (let [url-requisicao (str api-url "translate?sl=en&dl=pt&text=" frase)
          resposta (http/get url-requisicao {:accept :json})
          corpo (json/parse-string (:body resposta) true)]
      (or (retorna-primeiro-elemento corpo) frase))
    (catch Exception e
      (println "Erro ao traduzir en→pt:" frase "-" (.getMessage e))
      frase)))

(defn -main []
  (println (portugues-ingles "uma bola quadrada"))
  (println (ingles-portugues "a red dog")))
  (first (get-in conteudo [:translations :possible-translations])))

(defn portugues-ingles [frase]
  (let [url-requisicao (str api-url "translate?sl=pt&dl=en&text=" (substituir-espacos frase))
        resposta (http/get url-requisicao {:accept :json})
        corpo (json/parse-string (:body resposta) true)]
    (retorna-primeiro-elemento corpo)))

(defn ingles-portugues [frase]
  (let [url-requisicao (str api-url "translate?sl=en&dl=pt&text=" (substituir-espacos frase))
        resposta (http/get url-requisicao {:accept :json})
        corpo (json/parse-string (:body resposta) true)]
    (retorna-primeiro-elemento corpo)))
