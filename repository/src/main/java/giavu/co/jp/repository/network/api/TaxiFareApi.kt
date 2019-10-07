package giavu.co.jp.repository.network.api

import giavu.co.jp.repository.model.CityDetails
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
interface TaxiFareApi {

    @GET("/entity?key=NLYBQw5mh26J")
    suspend fun  findNearestSupportCity(@Query("location") location: String): CityDetails

}