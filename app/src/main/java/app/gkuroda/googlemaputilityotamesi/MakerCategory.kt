package app.gkuroda.googlemaputilityotamesi

import androidx.annotation.DrawableRes

enum class MakerCategory(val id: Int, @DrawableRes val icon: Int, val categoryText: String) {
    POST(1, R.drawable.post, "ポスト"),
    TELEPHONE(2, R.drawable.telephone, "公衆電話"),
    PARK(3, R.drawable.park, "公園"),
    OTHER(4, R.drawable.other, "その他");

    companion object {
        fun typeOf(categoryId: Int): MakerCategory {
            return values().firstOrNull { it.id == categoryId } ?: OTHER
        }
    }
}