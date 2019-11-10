package giavu.co.jp.taxifare.map

class InitialDropoffLocation(
    latitude: Double,
    longitude: Double
) {

    companion object {
        // 用途はある地点を南西へずらした位置へ変換する (地域により差があるが約200m)
        private const val DIFFERENCE = 0.002f
    }

    val latitude = latitude - DIFFERENCE

    val longitude = longitude - DIFFERENCE
}
