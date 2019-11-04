package giavu.co.jp.domain.model

import java.io.Serializable

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */

data class Location(
    val pickup: Coordinate,
    val dropoff: Coordinate
) : Serializable {
    data class Coordinate(
        val lat: Double,
        val lon: Double
    ) : Serializable
}


