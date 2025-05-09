(define (problem hanoi-3-3)
(:domain hanoi)
;; three rings three piques problem
(:objects small medium big - ring 
          first second third - tower)
;; three rings on first tower in order from small on top to big on the bottom
(:init (clear small) (on small medium) (on medium big) (smaller small medium) (smaller small big) (smaller medium big)
 (ontower small first) (ontower medium first) (ontower big first)
 (towerempty second) (towerempty third)
 (onbase big)
 (handempty)
)
(:goal (and (on small medium) (on medium big) (ontower small third) (ontower medium third) (ontower big third)))
)
