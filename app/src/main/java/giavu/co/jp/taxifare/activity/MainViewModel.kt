package giavu.co.jp.taxifare.activity

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import giavu.co.jp.domain.usecase.FetchNearestSupportCityUseCase
import giavu.co.jp.taxifare.map.FetchMyLocationUseCase
import giavu.co.jp.taxifare.map.MapModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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
    private val fetchMyLocationUseCase: FetchMyLocationUseCase,
    private val fetchNearestSupportCityUseCase: FetchNearestSupportCityUseCase
) : AndroidViewModel(application), LifecycleObserver {

    private lateinit var model: MapModel
    private val _centerLocation = MutableLiveData<LatLng>()
    val centerLocation: LiveData<LatLng>
        get() = _centerLocation

    fun initialize(
        map: GoogleMap
    ) {
        Timber.d("initialize")
        model = MapModel(context = getApplication(), map = map)
        model.initialize()
        model.centerLocation.observeForever {
            _centerLocation.value = it
        }
    }


    fun fetch() {
        Timber.d("fetching")
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    val location = fetchMyLocationUseCase().map {
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

    @SuppressLint("CheckResult")
    fun moveMyLocation() {
        fetchMyLocationUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = Timber::w,
                onSuccess = {
                    LatLng(it.latitude, it.longitude).let { location ->
                        model.moveCamera(location)
                    }
                }
            )
    }

    fun requestMyLocation() {
        model.requestMyLocation()
    }
}