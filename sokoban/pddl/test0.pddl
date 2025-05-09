(define (problem test1)
	(:domain sokoban)
	(:objects
		c0_0
		c0_1
		c0_2
		c1_0
		c1_1
		c1_2
	)
	(:init
		(isPlayer c0_0)
		(leftOf c0_0 c0_1)
		(isCrate c0_1)
		(leftOf c0_1 c0_2)
		(isClear c0_2)
		(below c1_0 c0_0)
		(isClear c1_0)
		(leftOf c1_0 c1_1)
		(below c1_1 c0_1)
		(isClear c1_1)
		(leftOf c1_1 c1_2)
		(below c1_2 c0_2)
		(isClear c1_2)
	)

	(:goal (and
			(isCrate c0_2)
		)
	)
)
