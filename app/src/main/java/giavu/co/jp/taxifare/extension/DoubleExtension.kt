package giavu.co.jp.taxifare.extension

import java.math.RoundingMode

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */
fun Double.roundWithDigit(pos: Int): Double {
    return this.toBigDecimal().setScale(pos, RoundingMode.CEILING).toDouble()
}