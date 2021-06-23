package app.gkuroda.googlemaputilityotamesi

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomClusterRenderer(
    val context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<MapItem>?
) : DefaultClusterRenderer<MapItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MapItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        val itemMakerDescription = item.getMakerDescription()

        //カテゴリIDをMakerCategoryへ変換
        val makerCategory = MakerCategory.typeOf(itemMakerDescription.makerCategory)

        // マーカーとして使うにはBitmapである必要があるのでDrawableを取得したらBitmapへ変換する
        val iconBitmap = context?.getDrawable(makerCategory.icon)?.toBitmap()

        if (iconBitmap != null) {
            // 適用するにはBitmapDescriptorという形式である必要があるためbitmapを変換する
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(iconBitmap)
            markerOptions.icon(bitmapDescriptor)
        }

    }
}