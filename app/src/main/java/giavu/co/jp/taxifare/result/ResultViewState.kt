package giavu.co.jp.taxifare.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import giavu.co.jp.repository.model.TaxiFare

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-06
 */
class ResultViewState(val taxiFare: LiveData<TaxiFare>) {

    val totalFare: LiveData<String> = Transformations.map(taxiFare) {
        it.total_fare.toString()
    }

    val initialFare: LiveData<String> = Transformations.map(taxiFare) {
        it.initial_fare.toString()
    }

    val meteredFare: LiveData<String> = Transformations.map(taxiFare) {
        it.metered_fare.toString()
    }

}