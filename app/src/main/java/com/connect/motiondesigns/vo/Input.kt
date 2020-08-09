package com.connect.motiondesigns.vo

import com.connect.motiondesigns.entity.Item
import com.connect.motiondesigns.enums.SortOrder
import java.math.BigDecimal

data class Input (
        var sortOrder: SortOrder,
        var maxPiecesPerPack: Long,
        var maxWeightPerPack: BigDecimal,
        var items: MutableList<Item> = mutableListOf()
)