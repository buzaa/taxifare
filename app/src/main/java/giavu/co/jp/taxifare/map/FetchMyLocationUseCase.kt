package giavu.co.jp.taxifare.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import io.reactivex.Single

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-07
 */
class FetchMyLocationUseCase(private val context: Context) {

    private val client by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    operator fun invoke(): Single<Location> {
        return Single.create { singleEmitter ->
            if (checkSelfPermission()) {
                client.lastLocation.apply {
                    addOnSuccessListener {
                        if (singleEmitter.isDisposed.not()) {
                            singleEmitter.onSuccess(it)
                        }
                    }
                    addOnFailureListener {
                        if (singleEmitter.isDisposed.not()) {
                            singleEmitter.onError(it)
                        }
                    }
                }
            } else {
                singleEmitter.onError(SecurityException())
            }
        }
    }

    private fun checkSelfPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).let {
            it == PackageManager.PERMISSION_GRANTED
        }
    }
}