package giavu.co.jp.taxifare.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.activity.MainViewModel
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MapFragment : SupportMapFragment() {

    private val viewModel: MainViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync { googleMap ->
            viewModel.initialize(googleMap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val origin = super.onCreateView(layoutInflater, viewGroup, bundle) as ViewGroup
        return inflater.inflate(R.layout.layout_fragment_map, origin)
    }
}