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



;;; An introduction to Logic Programming with core.logic - Singapore Clojure Meetup

;; 1. родственные связи:

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



;; 2. списки

(comment
  (run* [q]
        (membero q [1 2 3])
        (membero q [3 4 2 0])
        )
  ; => (2 3)
  )

(defne membero
  "A relation where l is a collection, such that l contains x."
  [x l]
  ([_ [x . tail]])
  ([_ [head . tail]]
   (membero x tail)))

; непонятно!
(defne appendo
  "A relation where x, y and z are proper collections,
such that z is x appended to y"
  [x y z]
  ([() _ y])
  ([[a . d] _ [a . r]] (appendo d y r)))

(comment
  (run* [q]
        (appendo [1 2] [3 4] q)
        )
  ; => ((1 2 3 4)) 
  )

(comment
  (run* [q]
        (appendo [1 2] q [1 2 3 4] ))
  ; => ((3 4))
  )

(comment
  (run* [q]
        (appendo [1 2] [3 4] [1 2 3 4]))
  ; => (_0)
  )

(comment
  (run* [q]
        (appendo (range 1000) q (range 1002)))
  ; => ((1000 1001))
  )

(comment
  (run* [q]
        (appendo (range 1002) q (range 100)))
  ; => ()
  )



;; 3. Zebra?!

; TODO https://youtu.be/lzCIyvFgUVk?t=1450