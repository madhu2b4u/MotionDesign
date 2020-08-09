package com.connect.motiondesigns.entity

import com.connect.motiondesigns.entity.Item
import java.math.BigDecimal

data class Pack(
    var packageNumber: Long,
    var products: MutableList<Item> = mutableListOf(),
    var packLength: Long = 0L,
    var packWeight: BigDecimal = BigDecimal.ZERO
)