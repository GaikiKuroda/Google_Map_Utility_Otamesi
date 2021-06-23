package app.gkuroda.googlemaputilityotamesi

import java.io.Serializable

data class MakerDescription(
    val name: String,
    val comment: String,
    val makerCategory: Int,
) : Serializable