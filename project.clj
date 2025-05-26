(defproject api-nutricao "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://api-nutricao-production.up.railway.app"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [clj-http "3.13.0"]
                 [cheshire "6.0.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :main api-nutricao.core
  :ring {:handler api-nutricao.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})