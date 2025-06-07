(ns api-nutricao.exercicios.exercicio-controller
  (:require [api-nutricao.data.listas :as data]
            [api-nutricao.exercicios.exercicio-repository :as exercicio-repository])
  )
;{
; "name": "Skiing, water skiing",
; "calories_per_hour": 354,
; "duration_minutes": 60,
; "total_calories": 354
; },

(defn buscar-exercicio [nome-exercicio peso duracao]
  (exercicio-repository/buscar-exercicios nome-exercicio peso duracao)
  )

;&weight=500&duration=180
(defn registrar-perda [novo-exercicio]
  (swap! data/lista-perda conj novo-exercicio)
  )