package giavu.co.jp.domain.usecase

import giavu.co.jp.repository.model.CityDetails
import giavu.co.jp.repository.network.api.TaxiFareApi

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class FetchNearestSupportCity(
    private val api: TaxiFareApi
) {

    suspend operator fun invoke(location: String): CityDetails {
        return api.findNearestSupportCity(location = location)
    }
}