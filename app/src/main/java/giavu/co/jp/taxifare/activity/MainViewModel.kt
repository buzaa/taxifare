package giavu.co.jp.taxifare.activity

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import giavu.co.jp.domain.model.Location
import giavu.co.jp.domain.usecase.FetchNearestSupportCityUseCase
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.extension.Visibility
import giavu.co.jp.taxifare.map.FetchMyLocationUseCase
import giavu.co.jp.taxifare.map.InitialDropoffLocation
import giavu.co.jp.taxifare.map.MapModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.co.japantaxi.brooklyn.domain.resource.ResourceProvider
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MainViewModel(
    val application: Application,
    private val fetchMyLocationUseCase: FetchMyLocationUseCase,
    private val fetchNearestSupportCityUseCase: FetchNearestSupportCityUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    enum class CameraState {
        MOVE,
        IDLE,
    }

    private lateinit var model: MapModel
    private val centerLocation = MutableLiveData<LatLng>()
    private val _calculateRequest = MutableLiveData<Location>()
    val calculateRequest: LiveData<Location>
        get() = _calculateRequest
    private val _pickupVisibility = MutableLiveData<Visibility>()
    val pickupVisibility: LiveData<Visibility>
        get() = _pickupVisibility
    private val _dropoffVisibility = MutableLiveData<Visibility>()
    val dropoffVisibility: LiveData<Visibility>
        get() = _dropoffVisibility
    private val _textMessage = MutableLiveData<String>()
    val textMessage: LiveData<String>
        get() = _textMessage
    private val _cameraState = MutableLiveData<CameraState>()
    val cameraState: LiveData<CameraState>
        get() = _cameraState
    private val _enableState = MutableLiveData<Boolean>()
    val enableState: LiveData<Boolean>
        get() = _enableState

    private val pickupLocation = MutableLiveData<Location.Coordinate>()

    fun initialize(
        map: GoogleMap
    ) {
        Timber.d("initialize")
        model = MapModel(context = application.applicationContext, map = map)
        model.initialize()
        model.startCameraEvent.observeForever {
            _cameraState.value = CameraState.MOVE
            _enableState.value = true
        }
        model.idleCameraEvent.observeForever {
            _cameraState.value = CameraState.IDLE
            _enableState.value = false
        }
        observeMap()
        initViewModel()

    }

    private fun initViewModel() {
        _pickupVisibility.value = Visibility.VISIBLE
        _dropoffVisibility.value = Visibility.GONE
        _textMessage.value = resourceProvider.getString(R.string.start_location)
    }

    fun selectPickup() {
        centerLocation.value?.let { point ->
            pickupLocation.value = Location.Coordinate(lat = point.latitude, lon = point.longitude)
            model.addMarker(resourceId = R.drawable.ic_start, location = point)
            model.animateCamera(
                LatLng(
                    InitialDropoffLocation(point.latitude, point.longitude).latitude,
                    InitialDropoffLocation(point.latitude, point.longitude).longitude
                )
            )
        }
        _pickupVisibility.value = Visibility.GONE
        _dropoffVisibility.value = Visibility.VISIBLE
        _textMessage.value = resourceProvider.getString(R.string.destination_location)
    }

    fun selectDropOff() {
        centerLocation.value?.let {
            model.addMarker(resourceId = R.drawable.ic_goal, location = it)
            pickupLocation.value?.let { pickup ->
                _calculateRequest.value = Location(
                    pickup = pickup,
                    dropoff = Location.Coordinate(it.latitude, it.longitude)
                )
            }
        }
    }

    private fun observeMap() {
        model.centerLocation.observeForever {
            Timber.d("Center : %s", it.toString())
            centerLocation.value = it
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