package com.connect.motiondesigns

import com.connect.motiondesigns.entity.Item
import com.connect.motiondesigns.entity.Pack
import com.connect.motiondesigns.service.aggregatePacks
import com.connect.motiondesigns.service.processInputLines
import com.connect.motiondesigns.service.sort
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class MotionDesignTest {

    @Test
    fun testNatural() {
        val standardList = mutableListOf<String>()
        standardList.add("NATURAL,40,500.0")
        standardList.add("1001,6200,30,9.653")
        standardList.add("2001,7200,50,11.21")
        println(standardList)

        val input = processInputLines(standardList)
        val output = sort(input)

        val expected = """
Pack Number: 1
1001,6200,30,9.653
2001,7200,10,11.21
Pack Length: 7200, Pack Weight: 401.69

Pack Number: 2
2001,7200,40,11.21
Pack Length: 7200, Pack Weight: 448.4
        """.trimIndent()

        Assert.assertEquals(expected, output.toString())
    }



    @Test
    fun testLongToShort() {
        val standardList = mutableListOf<String>()
        standardList.add("LONG_TO_SHORT,50,600.0")
        standardList.add("1001,6200,40,9.653")
        standardList.add("2001,8200,40,11.75")
        standardList.add("3001,7200,40,10.61")
        standardList.add("4001,9200,30,12.15")

        val input = processInputLines(standardList)
        val output = sort(input)

        val expected = """
Pack Number: 1
2001,8200,20,11.75
4001,9200,30,12.15
Pack Length: 9200, Pack Weight: 599.5

Pack Number: 2
3001,7200,30,10.61
2001,8200,20,11.75
Pack Length: 8200, Pack Weight: 553.3

Pack Number: 3
1001,6200,40,9.653
3001,7200,10,10.61
Pack Length: 7200, Pack Weight: 492.22
        """.trimIndent()

        TestCase.assertEquals(expected, output.toString())
    }

    @Test
    fun testShortToLong() {
        val standardList = mutableListOf<String>()
        standardList.add("SHORT_TO_LONG,50,600.0")
        standardList.add("1001,6200,40,9.653")
        standardList.add("2001,7200,70,10.61")
        standardList.add("3001,8200,40,11.75")
        println(standardList)

        val input = processInputLines(standardList)
        val output = sort(input)

        val expected = """
Pack Number: 1
1001,6200,40,9.653
2001,7200,10,10.61
Pack Length: 7200, Pack Weight: 492.22

Pack Number: 2
2001,7200,50,10.61
Pack Length: 7200, Pack Weight: 530.5

Pack Number: 3
2001,7200,10,10.61
3001,8200,40,11.75
Pack Length: 8200, Pack Weight: 576.1
        """.trimIndent()

        TestCase.assertEquals(expected, output.toString())
    }


    @Test
    fun testLongToShortByWeight() {
        val standardList = mutableListOf<String>()
        standardList.add("LONG_TO_SHORT,50,500.0")
        standardList.add("1001,6200,40,9.653")
        standardList.add("2001,8200,40,11.75")
        standardList.add("3001,7200,40,10.61")
        standardList.add("4001,9200,30,12.15")

        val input = processInputLines(standardList)
        val output = sort(input)

        val expected = """
Pack Number: 1
2001,8200,11,11.75
4001,9200,30,12.15
Pack Length: 9200, Pack Weight: 493.75

Pack Number: 2
3001,7200,15,10.61
2001,8200,29,11.75
Pack Length: 8200, Pack Weight: 499.9

Pack Number: 3
1001,6200,24,9.653
3001,7200,25,10.61
Pack Length: 7200, Pack Weight: 496.922

Pack Number: 4
1001,6200,16,9.653
Pack Length: 6200, Pack Weight: 154.448""".trimIndent()

        TestCase.assertEquals(expected, output.toString())
    }


    @Test
    fun testAggregatePacks() {

        val pack1 = Pack (
                packageNumber = 1,
                products = mutableListOf(
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal()),
                        Item(itemId = 1001,length = 6200,quantity = 1, weight = 9.653.toBigDecimal())
                        )
        )

        val pack2 = Pack (
                packageNumber = 2,
                products = mutableListOf(
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal())
                )
        )

        val pack3 = Pack (
                packageNumber = 3,
                products = mutableListOf(
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 2001,length = 7200,quantity = 1, weight = 10.61.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 4100,length = 9200,quantity = 1, weight = 12.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal()),
                        Item(itemId = 3100,length = 8200,quantity = 1, weight = 11.75.toBigDecimal())
                )
        )

        val packs:MutableList<Pack> = mutableListOf(pack1, pack2, pack3)

        aggregatePacks(packs)

        println(packs)

    }


}