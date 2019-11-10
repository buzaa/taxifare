package giavu.co.jp.taxifare.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import giavu.co.jp.repository.model.TaxiFare
import giavu.co.jp.taxifare.R
import jp.co.japantaxi.brooklyn.domain.resource.ResourceProvider

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-06
 */
class ResultViewState(
    val resourceProvider: ResourceProvider,
    val taxiFare: LiveData<TaxiFare>
) {

    val area: LiveData<String> = Transformations.map(taxiFare) {
        resourceProvider.getString(R.string.label_you_are_here, it.rate_area)
    }
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