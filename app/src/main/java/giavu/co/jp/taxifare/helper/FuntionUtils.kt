package giavu.co.jp.taxifare.helper

import android.content.res.Resources

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */
object FuntionUtils {

    fun pxFromDp(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }
}