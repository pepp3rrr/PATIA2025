;;;;;;;;;;;;;;;;;;;;;;;;;;:;
;;; Robot dans la salle  ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (domain gripper-strips)
  (:requirements :strips :typing)
  (:types room ball hand)
  (:predicates
        (inHand ?b - ball ?h - hand)
		(inRoom ?b - ball ?r - room)
        (at ?r - room)
		(isFree ?h - hand)
    )

   (:action move
       :parameters  (?from - room ?to - room)
       :precondition (at ?from)
       :effect (and (at ?to)
		     (not (at ?from)))
    )

   (:action pick
       :parameters (?b - ball ?r - room ?h - hand)
       :precondition  (and (at ?r) (isFree ?h) (inRoom ?b ?r))
       :effect (and (inHand ?b ?h)
		    (not (inRoom ?b ?r)) 
		    (not (isFree ?h)))
    )


   (:action drop
       :parameters  (?b - ball  ?r - room ?h - hand)
       :precondition  (and (at ?r) (inHand ?b ?h))
       :effect (and (inRoom ?b ?r) 
		    (isFree ?h)
		    (not (inHand ?b ?h))
        )
    )
)

