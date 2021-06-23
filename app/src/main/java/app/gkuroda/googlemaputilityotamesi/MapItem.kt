package app.gkuroda.googlemaputilityotamesi

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Segment(
    val position: LatLng,
    val title: String?,
    val snippet: String?,
    val makerDescription: MakerDescription
)

class MapItem(private val segment: Segment) : ClusterItem {

    override fun getSnippet(): String? = null // スニペットを使わないためnullを入れておく

    override fun getTitle(): String? = null  // マーカータイトルを表示するときのみ使う。今回は不要のためnull

    override fun getPosition(): LatLng = segment.position

    fun getMakerDescription(): MakerDescription = segment.makerDescription

}
