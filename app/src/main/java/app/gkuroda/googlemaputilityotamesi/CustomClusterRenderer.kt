package app.gkuroda.googlemaputilityotamesi

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

class CustomClusterRenderer(
    val context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<MapItem>?
) : DefaultClusterRenderer<MapItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MapItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        if (context == null) return

        val itemMakerDescription = item.getMakerDescription()

        //カテゴリIDをMakerCategoryへ変換
        val makerCategory = MakerCategory.typeOf(itemMakerDescription.makerCategory)

        // マーカーとして使うにはBitmapである必要があるのでDrawableを取得したらBitmapへ変換する
        val iconBitmap = ContextCompat.getDrawable(context, makerCategory.icon)?.toBitmap()

        if (iconBitmap != null) {
            // 適用するにはBitmapDescriptorという形式である必要があるためbitmapを変換する
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(iconBitmap)
            markerOptions.icon(bitmapDescriptor)
        }

    }

    override fun onBeforeClusterRendered(
        cluster: Cluster<MapItem>,
        markerOptions: MarkerOptions
    ) {
        val icon = makeIcon(cluster.size)
        if (icon != null) markerOptions.icon(icon)
    }

    override fun onClusterUpdated(cluster: Cluster<MapItem>, marker: Marker) {
        val icon = makeIcon(cluster.size)
        if (icon != null) marker.setIcon(icon)
    }

    /** クラスタのアイコンを生成する */
    private fun makeIcon(clusterSize: Int): BitmapDescriptor? {
        var result: BitmapDescriptor? = null
        val clusterIconGenerator = IconGenerator(context)

        if (context != null) {
            //確定nullだと面倒なので一番小さいものを詰めておく
            var clusterIcon = ContextCompat.getDrawable(context, R.drawable.cluster_a)

            //アイコンに表示するテキスト
            var clusterText = clusterSize.toString()

            //クラスタのサイズに合わせてアイコンを変更する
            when (clusterSize) {
                in 0 until 10 -> {
                    //それぞれにあったアイコンを指定
                    clusterIcon = ContextCompat.getDrawable(context, R.drawable.cluster_a)
                    clusterText = clusterSize.toString()
                }
                in 10 until 100 -> {
                    clusterIcon = ContextCompat.getDrawable(context, R.drawable.cluster_b)
                    clusterText = "${(clusterSize / 10)}0+"
                }
                in 100 until 1000 -> {
                    clusterIcon = ContextCompat.getDrawable(context, R.drawable.cluster_c)
                    clusterText = "${(clusterSize / 100)}00+"
                }
                in 1000 until 10000 -> {
                    clusterIcon = ContextCompat.getDrawable(context, R.drawable.cluster_d)
                    clusterText = "${(clusterSize / 1000)}000+"
                }
            }

            // アイコン用を生成するクラスにバックグラウンド画像を渡す
            clusterIconGenerator.setBackground(clusterIcon)

            // クラスタ内に表示するテキストを設定してBitmapDescriptorを生成する
            result = BitmapDescriptorFactory.fromBitmap(clusterIconGenerator.makeIcon(clusterText))

        }
        return result
    }
}