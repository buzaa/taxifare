package giavu.co.jp.domain.usecase

import giavu.co.jp.domain.model.TaxiFareParameter
import giavu.co.jp.repository.model.TaxiFare
import giavu.co.jp.repository.network.api.TaxiFareApi

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */
class FetchTaxiFareUseCase(
    private val api: TaxiFareApi,
    private val fetchNearestSupportCityUseCase: FetchNearestSupportCityUseCase
) {

    suspend operator fun invoke(parameter: TaxiFareParameter): TaxiFare {
        return api.getFare(
            city = fetchNearestSupportCityUseCase(parameter.pickup).handle,
            pickup = parameter.pickup,
            dropOff = parameter.dropoff
        )
    }
}