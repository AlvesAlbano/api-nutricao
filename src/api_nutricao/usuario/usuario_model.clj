(ns api-nutricao.usuario.usuario-model)

(defn usuario [altura peso idade sexo]
  {:altura altura
   :peso peso
   :idade idade
   :sexo sexo}
  )

(defn get-altura [usuario]
  (:altura usuario)
  )

(defn get-peso [usuario]
  (:peso usuario)
  )
(defn get-idade [usuario]
  (:idade usuario)
  )

(defn get-sexo [usuario]
  (:sexo usuario))