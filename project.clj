(defproject logics-test "0.1.0-SNAPSHOT"
  :description "core.logic test, and over"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-beta1"]
                 [com.datomic/datomic-free 
                  ; "0.9.5703.21"
                  "0.9.5561.59"
                  ]
                 [org.clojure/core.logic "0.8.11"]]
  :main ^:skip-aot logics-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
)
