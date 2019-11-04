package giavu.co.jp.taxifare.activity

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
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

    fun initialize(
        map: GoogleMap
    ) {
        Timber.d("initialize")
        model = MapModel(context = application.applicationContext, map = map)
        model.initialize()
        model.startCameraEvent.observeForever { _cameraState.value = CameraState.MOVE }
        model.idleCameraEvent.observeForever { _cameraState.value = CameraState.IDLE }
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
        }
    }

    private fun observeMap() {
        model.centerLocation.observeForever {
            Timber.d("Center : %s", it.toString())
            centerLocation.value = it
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

    fun moveCamera(location: LatLng) {
        model.moveCamera(location)
    }

    fun moveCameraAnimation(location: LatLng, callback: (() -> Unit)? = null) {
        model.animateCamera(location, callback)
    }

    fun requestMyLocation() {
        model.requestMyLocation()
    }

}