package giavu.co.jp.repository.network.api

import giavu.co.jp.repository.model.CityDetails
import giavu.co.jp.repository.model.TaxiFare
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
interface TaxiFareApi {

    @GET("/entity?key=NLYBQw5mh26J")
    suspend fun findNearestSupportCity(@Query("location") location: String): CityDetails

    @GET("/fare?key=NLYBQw5mh26J")
    suspend fun getFare(
        @Query("entity_handle") city: String,
        @Query("origin") pickup: String,
        @Query("destination") dropOff: String
    ): TaxiFare

}