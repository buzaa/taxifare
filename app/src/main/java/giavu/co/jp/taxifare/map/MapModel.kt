package giavu.co.jp.taxifare.map

import android.content.Context
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
    }
}