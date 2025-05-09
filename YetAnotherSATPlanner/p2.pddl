(define (problem strips-gripper-x-2)
   (:domain gripper-strips)
   (:objects ballone - ball
              roomone roomtwo - room
              handone - hand
       )
   (:init (inHand ballone handone) (at roomtwo)
          )
   (:goal (and (inRoom ballone roomone))
   )
)