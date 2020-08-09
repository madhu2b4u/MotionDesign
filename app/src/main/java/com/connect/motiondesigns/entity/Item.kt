package com.connect.motiondesigns.entity

import java.math.BigDecimal

data class Item(
        var itemId: Long,
        var length: Long = 0L,
        var quantity: Long = 0L,
        var weight: BigDecimal = BigDecimal.ZERO
)