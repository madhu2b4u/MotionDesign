package com.connect.motiondesigns.extensions

import java.math.BigDecimal

fun Any?.println() {
    println(this)
}

fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    return this.map { selector(it) }.fold(BigDecimal.ZERO, BigDecimal::add)
}
