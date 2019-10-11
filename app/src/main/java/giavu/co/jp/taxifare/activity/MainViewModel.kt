package giavu.co.jp.taxifare.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import giavu.co.jp.domain.usecase.FetchNearestSupportCityUseCase
import giavu.co.jp.taxifare.map.FetchMyLocationUseCase
import giavu.co.jp.taxifare.map.MapModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MainViewModel(
    application: Application,
    private val myLocationUseCase: FetchMyLocationUseCase,
    private val fetchNearestSupportCityUseCase: FetchNearestSupportCityUseCase
) : AndroidViewModel(application), LifecycleObserver {

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

    fun fetch() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    val location = myLocationUseCase().map {
                        it.latitude.toString().plus(",").plus(it.longitude.toString())
                    }.await()
                    fetchNearestSupportCityUseCase(location = location)
                }
            }.onSuccess {
                Timber.d(it.toString())
            }.onFailure {
                Timber.d(it)
            }
        }
    }
}