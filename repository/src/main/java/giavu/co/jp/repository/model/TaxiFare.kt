package giavu.co.jp.repository.model

data class TaxiFare(
    val extra_charges: List<ExtraCharge>,
    val flat_rates: List<Any>,
    val initial_fare: Double,
    val locale: String,
    val metered_fare: Double,
    val rate_area: String,
    val status: String,
    val tip_amount: Double,
    val tip_percentage: Int,
    val total_fare: Double
)