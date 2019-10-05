package giavu.co.jp.taxifare.map

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import giavu.co.jp.taxifare.activity.MainViewModel
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MapFragment : SupportMapFragment() {

    companion object {
        private const val KEY_DISPLAY_APP_SETTING = "key_display_app_setting"
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    private val mainViewModel: MainViewModel by inject()

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
    }

    override fun onActivityCreated(p0: Bundle?) {
        super.onActivityCreated(p0)
    }

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync { googleMap ->
            googleMap?.let {
                mainViewModel.init(it)
            }
        }

    }

    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        return super.onCreateView(p0, p1, p2)
    }
}