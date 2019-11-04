package giavu.co.jp.taxifare.result

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import giavu.co.jp.domain.model.Location
import giavu.co.jp.domain.model.TaxiFareParameter
import giavu.co.jp.domain.usecase.FetchTaxiFareUseCase
import giavu.co.jp.taxifare.extension.roundWithDigit
import giavu.co.jp.taxifare.helper.FuntionUtils
import giavu.co.jp.taxifare.map.FetchMyLocationUseCase
import giavu.co.jp.taxifare.map.MapModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.reactivestreams.Publisher
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */
class ResultViewModel(
    val application: Application,
    private val fetchMyLocationUseCase: FetchMyLocationUseCase,
    private val fetchTaxiFareUseCase: FetchTaxiFareUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_MAP_PADDING = 100.0f
    }

    private lateinit var model: MapModel
    private val _mapInitialized = MutableLiveData<Unit>()
    val mapInitialized: LiveData<Unit>
        get() = _mapInitialized

    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation: LiveData<LatLng>
        get() = _myLocation

    private val locationBounds = MutableLiveData<LatLngBounds>()

    fun initializeMap(map: GoogleMap, context: Context) {
        model = MapModel(
            context = context,
            map = map
        )
        model.initialize()
        _mapInitialized.postValue(Unit)
    }

    fun addMarker(@DrawableRes resourceId: Int, location: LatLng) {
        model.addMarker(resourceId, location)
    }

    fun loadMapLocation(location: LatLng?, myLocation: LatLng? = null) {
        val bounds = createLatLngBounds(location, myLocation)
        locationBounds.postValue(bounds)
    }

    private fun createLatLngBounds(location: LatLng?, myLocation: LatLng?): LatLngBounds? {
        return if (location != null || myLocation != null) {
            val builder = LatLngBounds.builder()
            location?.let { builder.include((it)) }
            myLocation?.let { builder.include((it)) }
            builder.build()
        } else {
            null
        }
    }

    @SuppressLint("CheckResult")
    fun setPublisherMapTopPadding(
        owner: LifecycleOwner,
        publisherMapTopPadding: Publisher<Int>
    ) {
        Observables.zip(
            Observable.fromPublisher(locationBounds.toPublisher(owner)),
            Observable.fromPublisher(publisherMapTopPadding)
        ).subscribeBy(
            onNext = { data ->
                val locationBounds = data.first
                val topPaddingMapCenterPoint = data.second
                setMapTopPadding(topPaddingMapCenterPoint)
                locationBounds?.let {
                    moveCamera(it, true)
                }
            },
            onError = {
                Timber.d(it)
            }
        )
    }

    private fun moveCamera(bounds: LatLngBounds?, isAnimate: Boolean = false) {
        if (bounds != null) {
            // 北東緯度経度(northeast)と南西緯度経度(southwest)が同じだったら一つが設定していると判断する
            val isOneLocation = bounds.northeast == bounds.southwest
            if (isOneLocation) {
                moveCameraOneLocation(bounds.northeast, isAnimate)
            } else {
                moveCameraMultiLocation(bounds, isAnimate)
            }
        }
    }

    private fun moveCameraOneLocation(location: LatLng, isAnimate: Boolean = false) {
        if (isAnimate) {
            model.animateCamera(location)
        } else {
            model.moveCamera(location)
        }
    }

    private fun moveCameraMultiLocation(bounds: LatLngBounds, isAnimate: Boolean = false) {
        val padding = FuntionUtils.pxFromDp(DEFAULT_MAP_PADDING).toInt()
        if (isAnimate) {
            model.animateCamera(bounds, padding)
        } else {
            model.moveCamera(bounds, padding)
        }
    }

    private fun setMapTopPadding(value: Int) {
        model.setPadding(0, value, 0, 0)
    }

    @SuppressLint("CheckResult")
    fun fetchMyLocation() {
        fetchMyLocationUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Timber.d(it)
                    _myLocation.postValue(null)
                },
                onSuccess = {
                    LatLng(it.latitude, it.longitude).let { location ->
                        _myLocation.postValue(location)
                    }
                }
            )
    }

    fun fetchTaxiFare(pickup: Location.Coordinate, dropoff: Location.Coordinate) {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchTaxiFareUseCase(
                    parameter = TaxiFareParameter(
                        pickup = pickup.lat.roundWithDigit(5).toString().plus(",").plus(pickup.lon.roundWithDigit(5).toString()),
                        dropoff = dropoff.lat.roundWithDigit(5).toString().plus(",").plus(dropoff.lon.roundWithDigit(5).toString())
                    ).also {
                        Timber.d(it.toString())
                    }
                )
            }.onSuccess { fares ->
                Timber.d(fares.toString())

            }.onFailure {
                Timber.d(it)
            }
        }
    }


}