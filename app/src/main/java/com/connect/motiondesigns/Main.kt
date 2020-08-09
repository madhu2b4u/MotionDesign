package com.connect.motiondesigns

import com.connect.motiondesigns.extensions.println
import com.connect.motiondesigns.service.processInputLines
import com.connect.motiondesigns.service.sort

fun main() {
    val inputLines = mutableListOf<String>()
    var inputLine = readLine()
    while (inputLine.isNullOrBlank().not()) {
        inputLines.add(inputLine!!)
        inputLine = readLine()
    }
    val input = processInputLines(inputLines)
    val output = sort(input)
    output.println()
}
