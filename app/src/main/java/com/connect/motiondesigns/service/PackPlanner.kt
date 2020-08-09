package com.connect.motiondesigns.service

import arrow.core.Try
import arrow.syntax.collections.tail
import com.connect.motiondesigns.constants.Constant.FIRST_LINE_SEGMENT_NUMBER
import com.connect.motiondesigns.constants.Constant.ITEM_LINE_SEGMENT_NUMBER
import com.connect.motiondesigns.constants.Constant.LINE_DELIMITER
import com.connect.motiondesigns.entity.Item
import com.connect.motiondesigns.entity.Pack
import com.connect.motiondesigns.enums.SortOrder
import com.connect.motiondesigns.extensions.sumByBigDecimal
import com.connect.motiondesigns.vo.Input
import com.connect.motiondesigns.vo.Output
import java.math.BigDecimal

fun sort(input: Input): Output {
    var flattenedItems = flattenItems(input.items)
    flattenedItems = sortItems(flattenedItems, input.sortOrder)
    val packs = createPacks(flattenedItems, input.maxWeightPerPack, input.maxPiecesPerPack)
    aggregatePacks(packs)
    return Output(packs = packs)
}

private fun flattenItems(items: List<Item>): List<Item> {
    return items.flatMap { item ->
        (1..item.quantity).map { item.copy(quantity = 1) }
    }
}

private fun sortItems(flatItemList: List<Item>, sortOrder: SortOrder): List<Item> {
    return when (sortOrder) {
        SortOrder.NATURAL -> flatItemList
        SortOrder.SHORT_TO_LONG -> flatItemList.sortedBy { it.length }
        SortOrder.LONG_TO_SHORT -> flatItemList.sortedByDescending { it.length }
    }
}

private fun createPacks(flatItemList: List<Item>, maxWeightPerPack: BigDecimal, maxPiecesPerPack: Long): MutableList<Pack> {
    val packs: MutableList<Pack> = mutableListOf()
    var newPack = Pack(packageNumber = 1)
    packs.add(newPack)
    var totalWeight = BigDecimal.ZERO
    var totalPiece = 0L
    flatItemList.forEach { item ->
        totalWeight += item.weight
        totalPiece = totalPiece.inc()
        if (newPackNeeded(totalWeight, totalPiece, maxWeightPerPack, maxPiecesPerPack)) {
            newPack = Pack(packageNumber = newPack.packageNumber.inc())
            totalPiece = 1
            totalWeight = item.weight
            packs += newPack
        }
        newPack.products.add(item)
    }
    return packs
}

fun aggregatePacks(packs: MutableList<Pack>) {
    packs.forEach { pack ->
        val weight = pack.products.sumByBigDecimal { it.weight }
        pack.packLength = pack.products.maxBy { it.length }!!.length
        pack.packWeight = weight
        pack.products = pack.products.groupBy { it.itemId }.map { entry ->
            entry.value.first().copy(quantity = entry.value.count().toLong())
        }.sortedBy { it.length }.toMutableList()
    }
}

private fun newPackNeeded(totalWeight: BigDecimal, totalPiece: Long, maxWeightPerPack: BigDecimal, maxPiecesPerPack: Long) =
        totalWeight > maxWeightPerPack || totalPiece > maxPiecesPerPack

fun processInputLines(inputLines: MutableList<String>): Input {
    val firstLine = inputLines.first()
    val firstLineItems = firstLine.split(LINE_DELIMITER)
    if (firstLineItems.size != FIRST_LINE_SEGMENT_NUMBER) {
        throw java.lang.IllegalArgumentException("First line must have $FIRST_LINE_SEGMENT_NUMBER segments. The actual value: $firstLine")
    }
    val (sortOrder, maxPiecesPerPack, maxWeightPerPack) = firstLineItems

    val items = inputLines.tail().map { line ->
        val itemInfo = line.split(LINE_DELIMITER)
        if (itemInfo.size != ITEM_LINE_SEGMENT_NUMBER) {
            throw java.lang.IllegalArgumentException("Item line must have $ITEM_LINE_SEGMENT_NUMBER segments. The actual value: $line")
        }
        val (itemId, length, quantity, weight) = line.split(LINE_DELIMITER)
        Item(
                itemId = Try { itemId.toLong() }
                        .fold(
                                parseFailure("itemId", itemId, "Long", line),
                                { validation(it, "itemId", line) }
                        ),
                length = Try { length.toLong() }
                        .fold(
                                parseFailure("length", length, "Long", line),
                                { validation(it, "length", line) }
                        ),
                quantity = Try { quantity.toLong() }
                        .fold(
                                parseFailure("quantity", "Long", quantity, line),
                                { validation(it, "quantity", line) }
                        ),
                weight = Try { weight.toBigDecimal() }
                        .fold(
                                parseFailure("weight", weight, "BigDecimal", line),
                                { validation(it, "weight", line) }
                        )
        )
    }.toMutableList()
    return Input(
            sortOrder = Try { SortOrder.valueOf(sortOrder) }
                    .fold(
                            { throw  IllegalArgumentException("Unsupported sort order [$sortOrder]", it) },
                            { it }
                    ),
            maxPiecesPerPack = Try { maxPiecesPerPack.toLong() }
                    .fold(
                            parseFailure("maxPiecesPerPack", maxPiecesPerPack, "Long", firstLine),
                            { validation(it, "maxPiecesPerPack", firstLine) }
                    ),
            maxWeightPerPack = Try { maxWeightPerPack.toBigDecimal() }
                    .fold(
                            parseFailure("maxWeightPerPack", maxWeightPerPack, "BigDecimal", firstLine),
                            { validation(it, "maxWeightPerPack", firstLine) }
                    ),
            items = items)
}

private fun parseFailure(fieldName: String, fieldValue: String, fieldType: String, line: String): (Throwable) -> Nothing =
        { throw  IllegalArgumentException("$fieldName is not a $fieldType. The actual value $fieldValue in line $line") }

fun validation(number: Long, fieldName: String, line: String): Long {
    if (number <= 0) {
        throw IllegalArgumentException("$fieldName should be greater than zero. The actual value $number in line $line")
    }
    return number
}

fun validation(number: BigDecimal, fieldName: String, line: String): BigDecimal {
    if (number <= BigDecimal.ZERO) {
        throw IllegalArgumentException("$fieldName should be greater than zero. The actual value $number in line $line")
    }
    return number
}

