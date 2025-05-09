(define (problem test16)
	(:domain sokoban)
	(:objects
		c0_0
		c0_1
		c0_2
		c0_3
		c0_4
		c0_5
		c1_0
		c1_1
		c1_2
		c1_3
		c1_4
		c1_5
		c2_0
		c2_1
		c2_2
		c2_3
		c2_4
		c2_5
		c3_0
		c3_1
		c3_2
		c3_3
		c3_4
		c3_5
		c3_6
		c3_7
		c4_0
		c4_1
		c4_2
		c4_3
		c4_4
		c4_5
		c4_6
		c4_7
		c5_0
		c5_1
		c5_2
		c5_3
		c5_4
		c5_5
		c5_6
		c5_7
		c6_0
		c6_1
		c6_2
		c6_3
		c6_4
		c6_5
		c6_6
		c6_7
		c7_0
		c7_1
		c7_2
		c7_3
		c7_4
		c7_5
		c8_0
		c8_1
		c8_2
		c8_3
		c8_4
		c8_5
	)
	(:init
		(isClear c0_0)
		(leftOf c0_0 c0_1)
		(isClear c0_1)
		(leftOf c0_1 c0_2)
		(leftOf c0_2 c0_3)
		(leftOf c0_3 c0_4)
		(leftOf c0_4 c0_5)
		(below c1_0 c0_0)
		(isClear c1_0)
		(leftOf c1_0 c1_1)
		(below c1_1 c0_1)
		(isClear c1_1)
		(leftOf c1_1 c1_2)
		(below c1_2 c0_2)
		(leftOf c1_2 c1_3)
		(below c1_3 c0_3)
		(isClear c1_3)
		(leftOf c1_3 c1_4)
		(below c1_4 c0_4)
		(isClear c1_4)
		(leftOf c1_4 c1_5)
		(below c1_5 c0_5)
		(below c2_0 c1_0)
		(leftOf c2_0 c2_1)
		(below c2_1 c1_1)
		(leftOf c2_1 c2_2)
		(below c2_2 c1_2)
		(leftOf c2_2 c2_3)
		(below c2_3 c1_3)
		(isClear c2_3)
		(leftOf c2_3 c2_4)
		(below c2_4 c1_4)
		(isPlayer c2_4)
		(leftOf c2_4 c2_5)
		(below c2_5 c1_5)
		(below c3_0 c2_0)
		(leftOf c3_0 c3_1)
		(below c3_1 c2_1)
		(isClear c3_1)
		(leftOf c3_1 c3_2)
		(below c3_2 c2_2)
		(isClear c3_2)
		(leftOf c3_2 c3_3)
		(below c3_3 c2_3)
		(isCrate c3_3)
		(leftOf c3_3 c3_4)
		(below c3_4 c2_4)
		(isCrate c3_4)
		(leftOf c3_4 c3_5)
		(below c3_5 c2_5)
		(leftOf c3_5 c3_6)
		(leftOf c3_6 c3_7)
		(below c4_0 c3_0)
		(leftOf c4_0 c4_1)
		(below c4_1 c3_1)
		(isClear c4_1)
		(leftOf c4_1 c4_2)
		(below c4_2 c3_2)
		(isClear c4_2)
		(leftOf c4_2 c4_3)
		(below c4_3 c3_3)
		(isCrate c4_3)
		(leftOf c4_3 c4_4)
		(below c4_4 c3_4)
		(isClear c4_4)
		(leftOf c4_4 c4_5)
		(below c4_5 c3_5)
		(isClear c4_5)
		(leftOf c4_5 c4_6)
		(below c4_6 c3_6)
		(isClear c4_6)
		(leftOf c4_6 c4_7)
		(below c4_7 c3_7)
		(below c5_0 c4_0)
		(leftOf c5_0 c5_1)
		(below c5_1 c4_1)
		(leftOf c5_1 c5_2)
		(below c5_2 c4_2)
		(isClear c5_2)
		(leftOf c5_2 c5_3)
		(below c5_3 c4_3)
		(isClear c5_3)
		(leftOf c5_3 c5_4)
		(below c5_4 c4_4)
		(isClear c5_4)
		(leftOf c5_4 c5_5)
		(below c5_5 c4_5)
		(isClear c5_5)
		(leftOf c5_5 c5_6)
		(below c5_6 c4_6)
		(isClear c5_6)
		(leftOf c5_6 c5_7)
		(below c5_7 c4_7)
		(below c6_0 c5_0)
		(isClear c6_0)
		(leftOf c6_0 c6_1)
		(below c6_1 c5_1)
		(leftOf c6_1 c6_2)
		(below c6_2 c5_2)
		(leftOf c6_2 c6_3)
		(below c6_3 c5_3)
		(isClear c6_3)
		(leftOf c6_3 c6_4)
		(below c6_4 c5_4)
		(isCrate c6_4)
		(leftOf c6_4 c6_5)
		(below c6_5 c5_5)
		(leftOf c6_5 c6_6)
		(below c6_6 c5_6)
		(leftOf c6_6 c6_7)
		(below c6_7 c5_7)
		(below c7_0 c6_0)
		(isClear c7_0)
		(leftOf c7_0 c7_1)
		(below c7_1 c6_1)
		(isClear c7_1)
		(leftOf c7_1 c7_2)
		(below c7_2 c6_2)
		(leftOf c7_2 c7_3)
		(below c7_3 c6_3)
		(isClear c7_3)
		(leftOf c7_3 c7_4)
		(below c7_4 c6_4)
		(isClear c7_4)
		(leftOf c7_4 c7_5)
		(below c7_5 c6_5)
		(below c8_0 c7_0)
		(isClear c8_0)
		(leftOf c8_0 c8_1)
		(below c8_1 c7_1)
		(isClear c8_1)
		(leftOf c8_1 c8_2)
		(below c8_2 c7_2)
		(leftOf c8_2 c8_3)
		(below c8_3 c7_3)
		(leftOf c8_3 c8_4)
		(below c8_4 c7_4)
		(leftOf c8_4 c8_5)
		(below c8_5 c7_5)
	)

	(:goal (and
			(isCrate c3_2)
			(isCrate c3_3)
			(isCrate c4_4)
			(isCrate c4_5)
		)
	)
)
