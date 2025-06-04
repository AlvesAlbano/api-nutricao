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

(defn exibir-alimentos [alimentos]
  (letfn [(mostrar [alimentos idx]
            (if (or (empty? alimentos) (>= idx 7))
              nil
              (let [alimento (first alimentos)
                    {:keys [description brandOwner servingSize foodCategory ingredients]} alimento
                    calorias (get-in alimento [:foodNutrients 0 :value])]
                (println (str idx ". " (clojure.string/upper-case description)
                              " (Marca: " (or brandOwner "-") ")"))
                (println (str "   Porção: " (or servingSize "Porção não informada")
                              " | Calorias: " (or calorias "Calorias não informadas") " kcal"))
                (println (str "   Categoria: " (or foodCategory "Categoria não informada")))
                (println (str "   Ingredientes: " (or ingredients "Ingredientes não informados")))
                (recur (rest alimentos) (inc idx)))))]
    (mostrar alimentos 0)))

(defn adicionar-refeicao-interativo []
  (println "Digite o nome do alimento:")
  (let [nome (read-line)
        alimentos (buscar-alimento-api nome)
        primeiros (take 7 alimentos)]
    (exibir-alimentos primeiros)
    (println "Selecione o número do alimento (0 a 6):")
    (let [idx (Integer/parseInt (read-line))
          alimento (nth primeiros idx nil)]
      (if alimento
        (do
          (adicionar-refeicao alimento)
          (println "Alimento adicionado com sucesso!"))
        (println "Índice inválido.")))))
