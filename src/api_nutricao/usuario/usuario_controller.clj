(ns api-nutricao.usuario.usuario-controller
  (:require
    [api-nutricao.data.listas :as data]
    [cheshire.core :as json])
  )

(defn cadastrar-usuario [usuario]
  (reset! data/usuario-cadastrado usuario)
  )

(defn get-usuario []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string @data/usuario-cadastrado)}
  )