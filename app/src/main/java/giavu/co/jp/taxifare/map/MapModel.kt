package giavu.co.jp.taxifare.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MapModel(
    private val context: Context,
    private val map: GoogleMap,
    private val initialLocation: LatLng? = null,
    private val initialZoomLevel: Float? = null
) {
    companion object {
        private val TOKYO_STATION_LOCATION = LatLng(35.681167, 139.767052)
        private const val DEFAULT_ZOOM_LEVEL = 15.0f
        private const val ADJUST_ZOOM_LEVEL = 18.0f
    }

    private val _centerLocation = MutableLiveData<LatLng>()
    val centerLocation: LiveData<LatLng>
        get() = _centerLocation

    private val _zoomLevel = MutableLiveData<Float>()
    val zoomLevel: LiveData<Float>
        get() = _zoomLevel

    private val _isAdjustSkippable = MutableLiveData<Boolean>()
    val isAdjustSkippable: LiveData<Boolean>
        get() = _isAdjustSkippable

    fun initialize() {
        Timber.d("initialize")
        map.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            isBuildingsEnabled = false
            isIndoorEnabled = false
            isTrafficEnabled = false
        }

        map.uiSettings.apply {
            isRotateGesturesEnabled = false
            isCompassEnabled = false
            isIndoorLevelPickerEnabled = false
            isMapToolbarEnabled = false
            isMyLocationButtonEnabled = false
            isScrollGesturesEnabled = true
            isTiltGesturesEnabled = false
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = true
            isScrollGesturesEnabledDuringRotateOrZoom = false
        }

        val location = initialLocation ?: TOKYO_STATION_LOCATION
        val zoom = initialZoomLevel ?: DEFAULT_ZOOM_LEVEL

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom))

        _zoomLevel.observeForever {
            _isAdjustSkippable.value = it?.let { ADJUST_ZOOM_LEVEL <= it } ?: false
        }
        _zoomLevel.value = map.cameraPosition.zoom
        requestMyLocation()
    }

    fun requestMyLocation() {
        if (hasLocationPermission()) {
            @SuppressLint("MissingPermission")
            map.isMyLocationEnabled = true
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}