package app.gkuroda.googlemaputilityotamesi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var googleMapView: MapView

    private var googleMap: GoogleMap? = null

    private lateinit var mapCluster: ClusterManager<MapItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleMapView = mapView
        googleMapView.onCreate(savedInstanceState)
        googleMapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        googleMapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        googleMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        googleMapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        mapCluster = createClusterManager()

        //マーカーのアイコンをタップしたときの動作
        mapCluster.setOnClusterItemClickListener {

            return@setOnClusterItemClickListener true
        }

        //クラスターアイコンをタップしたときの動作
        mapCluster.setOnClusterClickListener { cluster ->

            return@setOnClusterClickListener true
        }

        //mapのカメラを移動したときに発生するイベント
        googleMap?.setOnCameraIdleListener {
            //イベントをmapCluster側にも伝えてあげる
            //コレをしないとクラスターの再描画等が発生しない
            mapCluster.onCameraIdle()
        }


        // Cluster関連の解説なので邪魔なデフォルトのボタン類を非表示
        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
        // 屋内マップを非表示にする
        googleMap?.isIndoorEnabled = false
        mapCluster.renderer.setAnimation(false)

        mapCluster.cluster()

        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(35.6900, 139.7000), 6F))

    }

    private fun createClusterManager(): ClusterManager<MapItem> {
        val manager = ClusterManager<MapItem>(this, googleMap)
        val initialList = makeMapMakers()
        manager.addItems(initialList)
        return manager
    }


    private fun makeMapMakers(): List<MapItem> {
        val itemList = mutableListOf<MapItem>()
        val baseLat = 35.6900
        val baseLng = 139.7000
        for (i in 1..1000) {
            itemList.add(
                MapItem(
                    Segment(
                        position = LatLng(
                            baseLat + Random.nextDouble(0.0, i * 0.001),
                            baseLng + Random.nextDouble(0.0, i * 0.001)
                        ),
                        title = null,
                        snippet = null,
                        makerDescription = MakerDescription(
                            name = "ポスト$i",
                            comment = "赤いポストです。$i",
                            makerCategory = MakerCategory.POST.id,
                        )
                    )
                )
            )

            itemList.add(
                MapItem(
                    Segment(
                        position = LatLng(
                            baseLat - Random.nextDouble(0.0, i * 0.001),
                            baseLng + Random.nextDouble(0.0, i * 0.001)
                        ),
                        title = null,
                        snippet = null,
                        makerDescription = MakerDescription(
                            name = "公衆電話$i",
                            comment = "昔ながらの公衆電話です。$i",
                            makerCategory = MakerCategory.TELEPHONE.id,
                        )
                    )
                )
            )

            itemList.add(
                MapItem(
                    Segment(
                        position = LatLng(
                            baseLat + Random.nextDouble(0.0, i * 0.001),
                            baseLng - Random.nextDouble(0.0, i * 0.001)
                        ),
                        title = null,
                        snippet = null,
                        makerDescription = MakerDescription(
                            name = "公園$i",
                            comment = "遊具もある公園です。$i",
                            makerCategory = MakerCategory.PARK.id,
                        )
                    )
                )
            )

            itemList.add(
                MapItem(
                    Segment(
                        position = LatLng(
                            baseLat - Random.nextDouble(0.0, i * 0.001),
                            baseLng - Random.nextDouble(0.0, i * 0.001)
                        ),
                        title = null,
                        snippet = null,
                        makerDescription = MakerDescription(
                            name = "その他$i",
                            comment = "ポストでも公衆電話でも公園でもない何か。$i",
                            makerCategory = MakerCategory.OTHER.id,
                        )
                    )
                )
            )
        }

        return itemList
    }
}