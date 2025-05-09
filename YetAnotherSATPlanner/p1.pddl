(define (problem strips-gripper-x-1)
   (:domain gripper-strips)
   (:objects ballone - ball
              roomone - room
              handone - hand
       )
   (:init (inHand ballone handone) (at roomone)
          )
   (:goal (and (inRoom ballone roomone))
   )
)