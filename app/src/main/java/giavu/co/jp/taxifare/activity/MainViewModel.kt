package giavu.co.jp.taxifare.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.maps.GoogleMap
import giavu.co.jp.taxifare.map.MapModel

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    enum class CameraState {
        MOVE,
        IDLE,
    }

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