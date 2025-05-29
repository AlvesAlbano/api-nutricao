# Etapa de build
FROM clojure:lein-2.10.0 as builder

WORKDIR /app

COPY . .

# Baixa as dependências antecipadamente (melhora caching)
RUN lein deps

# Etapa de runtime (pode ser a mesma imagem para simplificar)
FROM clojure:lein-2.10.0

WORKDIR /app

COPY --from=builder /app /app

# Expõe a porta usada pela aplicação
EXPOSE 3000

# Comando para rodar a aplicação
CMD ["lein", "run"]