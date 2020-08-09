package com.connect.motiondesigns.vo

import com.connect.motiondesigns.entity.Pack

data class Output(
        var packs: MutableList<Pack> = mutableListOf()
) {
    override fun toString(): String {
        return packs.joinToString("\n") { pack ->
            val lines = mutableListOf<String>()
            lines.add("Pack Number: ${pack.packageNumber}")
            lines.addAll(pack.products
                    .map {
                        "${it.itemId},${it.length},${it.quantity},${it.weight.stripTrailingZeros()}"
                    })
            lines.add("Pack Length: ${pack.packLength}, Pack Weight: ${pack.packWeight.stripTrailingZeros()}")
            lines.joinToString("\n").plus("\n")
        }.dropLast(1)
    }
}