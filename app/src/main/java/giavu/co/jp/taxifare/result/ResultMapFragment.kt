package giavu.co.jp.taxifare.result

import android.os.Bundle
import com.google.android.gms.maps.SupportMapFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */
class ResultMapFragment : SupportMapFragment() {

    private val viewModel: ResultViewModel by sharedViewModel()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        getMapAsync { googleMap ->
            viewModel.initializeMap(
                map = googleMap,
                context = this@ResultMapFragment.requireContext()
            )
        }
    }

}