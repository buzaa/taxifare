package giavu.co.jp.taxifare.activity

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MainViewModel(val application: Application): ViewModel() {

    private val fetchMyLocationUseCase: FetchMyLocationUseCase by application.inject()
    private val fetchNearestSupportCityUseCase: FetchNearestSupportCityUseCase by application.inject()

    private lateinit var model: MapModel
    private val _centerLocation = MutableLiveData<LatLng>()

    fun initialize(
        map: GoogleMap
    ) {
        Timber.d("initialize")
        model = MapModel(context = application.applicationContext, map = map)
        model.initialize()
        observeMap()

    }

    private fun observeMap() {
        model.centerLocation.observeForever {
            Timber.d("Center : %s", it.toString())
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

    fun setPickup() {
        Timber.d("Pickup%s", _centerLocation.value.toString())
    }
}