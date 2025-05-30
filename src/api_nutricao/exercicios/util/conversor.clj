(ns api-nutricao.exercicios.util.conversor)

(defn libras-kilo [libras]
  (/ libras 2.205))

(defn kilo-libras [kilo]
  (* kilo 2.205)
  )