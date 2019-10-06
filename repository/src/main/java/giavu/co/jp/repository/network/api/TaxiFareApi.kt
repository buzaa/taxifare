package giavu.co.jp.repository.network.api

import giavu.co.jp.repository.model.CityDetails
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
interface TaxiFareApi {

    @GET("/entity?key=dFAWsAtfv9jT")
    suspend fun  findNearestSupportCity(@Query("location") location: String): CityDetails

}