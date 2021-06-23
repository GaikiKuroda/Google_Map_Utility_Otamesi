package app.gkuroda.googlemaputilityotamesi

import androidx.annotation.DrawableRes

enum class MakerCategory(val id: Int, @DrawableRes val icon: Int) {
    POST(1, R.drawable.post),
    TELEPHONE(2, R.drawable.telephone),
    PARK(3, R.drawable.park),
    OTHER(4, R.drawable.other);

    companion object {
        fun typeOf(categoryId: Int): MakerCategory {
            return values().firstOrNull { it.id == categoryId } ?: OTHER
        }
    }
}