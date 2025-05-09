;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Taquin
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (domain taquin)
  (:requirements :strips :typing)
  (:types cell number)
  (:predicates (on ?n - number ?c - cell)
  	(empty ?c - cell)
	(adjacent ?firstc ?secondc)
  )
	;; move through adjacent cells
  (:action move
	     :parameters (?n - number ?moveto - cell ?movefrom - cell)
	     :precondition (and (empty ?moveto) (on ?n ?movefrom) (adjacent ?movefrom ?moveto))
	     :effect
		 (and
		 	(not (on ?n ?movefrom))
			(not (empty ?moveto))
			(empty ?movefrom)
			(on ?n ?moveto)
		)
	)
)
