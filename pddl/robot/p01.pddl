(define (problem strips-gripper-x-1)
   (:domain gripper-strips)
   (:objects ballone balltwo - ball
              roomone roomtwo - room
              handone handtwo - hand
       )
   (:init (isFree handone) (isFree handtwo) (inRoom ballone roomtwo) (inRoom balltwo roomone) (at roomtwo)
          )
   (:goal (and (inRoom ballone roomone) (inRoom balltwo roomtwo))
   )
)