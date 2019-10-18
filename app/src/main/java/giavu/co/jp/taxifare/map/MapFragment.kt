package giavu.co.jp.taxifare.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.SupportMapFragment
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.activity.MainViewModel
import giavu.co.jp.taxifare.extension.setOnProtectBarrageClickListener
import kotlinx.android.synthetic.main.layout_fragment_map.*
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MapFragment : SupportMapFragment() {

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    private val viewModel: MainViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync { googleMap ->
            viewModel.initialize(googleMap)
        }
        my_location.setOnProtectBarrageClickListener {
            requestPermissionIfNeeds()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                for (i in permissions.indices) {
                    if (Manifest.permission.ACCESS_FINE_LOCATION == permissions[i] &&
                        grantResults[i] == PackageManager.PERMISSION_GRANTED
                    ) {
                        viewModel.moveMyLocation()
                        viewModel.requestMyLocation()
                    } else {
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_LOCATION_PERMISSION
                        )
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        }
    }

    private fun requestPermissionIfNeeds() {
        val permissionResultFineLocation = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionResultFineLocation == PackageManager.PERMISSION_GRANTED) {
            viewModel.moveMyLocation()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}