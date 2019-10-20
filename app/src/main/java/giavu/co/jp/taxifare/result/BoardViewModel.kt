package giavu.co.jp.taxifare.result

import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-20
 */
class BoardViewModel : ViewModel() {

    fun selectPickup() {
        Timber.d("pickup")
    }

    fun selectDropOff() {
        Timber.d("dropoff")
    }

}