package giavu.co.jp.taxifare.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.maps.GoogleMap

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-12
 */
class MapViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private lateinit var mapModel: MapModel
    
    fun init(
        map: GoogleMap
    ) {
        mapModel = MapModel(
            context = getApplication(),
            map = map
        )
        mapModel.initialize()
    }
}