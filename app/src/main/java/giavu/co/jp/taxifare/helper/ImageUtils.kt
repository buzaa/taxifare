package giavu.co.jp.taxifare.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


/**
 * @Author: Hoang Vu
 * @Date:   2019-10-31
 */
object ImageUtils {

    fun getBitmapDescriptor(context: Context, id: Int): BitmapDescriptor {
        val vectorDrawable = getDrawable(context, id) as VectorDrawable

        // val h = vectorDrawable.intrinsicHeight
        // val w = vectorDrawable.intrinsicWidth
        val h = vectorDrawable.intrinsicHeight.div(8)
        val w = vectorDrawable.intrinsicWidth.div(8)

        vectorDrawable.setBounds(0, 0, w, h)
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bm)
    }
}