package giavu.co.jp.taxifare.extension

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onClick")
fun setClick(view: View, listener: View.OnClickListener) {
    view.setOnProtectBarrageClickListener(listener)
}