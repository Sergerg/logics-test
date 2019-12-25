(ns logics-test.core
  ;(:gen-class)
  ;(:require [clojure.core.logic :as l])
  ;(:use [clojure.core.logic :as l])
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic.pldb :refer [db with-db db-rel]]
            [clojure.core.logic :refer [all == != lvar run run* defne fresh]]
            [clojure.core.logic :as ll])
  )

;; LOGIC

(run* [q]
      (== q true))
;;=> (true)



; (defn -main
;   "I don't do a whole lot ... yet."
;   [& args]
;   (println "Hello, World!"))

; родственные связи:

(db-rel parent x y)

(def genealogy
  (db 
   [parent :john :Bobby]
   [parent :mary :Bobby]
   [parent :Bobby :SUSAN]
   ))

(comment 
  (with-db genealogy
    (run* [q]
          (parent q :Bobby)
          ))
  ; => (:john :mary)
  )

(comment
  (with-db genealogy
    (run* [q]
          (parent :john q)
          ))
  ; => :Bobby
  )

(defn sibling [x y]
  (fresh [p]
    (all
     (parent x p)
     (parent y p)
     (!= x y)))
  )

(comment
  (with-db genealogy
    (run* [q]
          (sibling q :john) ;; родной брат!
          ;; (grandparent x y)
          ))
  ; => (:mary)
  )

(defn grandparent [x gp]
  (fresh [p]
    (all
     (parent x p)
     (parent p gp)))
  )

(comment
  (with-db genealogy
    (run* [q]
          (grandparent q :SUSAN)
          )
    )
  ; => (:john :mary)
  )

(comment
  (with-db genealogy
    (run* [q]
          (grandparent :john q)))
  ; => (:SUSAN)
  )

(comment
  (with-db genealogy
    (run* [q]
      (fresh [a b]
        (grandparent a b)
        (ll/== q [a b]))
    )
  )
  ; => ([:john :SUSAN] [:mary :SUSAN])
  ; => (:SUSAN)
  )
;; Как бы напрашивается БД!
