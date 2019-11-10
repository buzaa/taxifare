package giavu.co.jp.taxifare.extension

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onClick")
fun setClick(view: View, listener: View.OnClickListener) {
    view.setOnProtectBarrageClickListener(listener)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visibility: Visibility?) {
    visibility ?: return
    view.visibility = when (visibility) {
        Visibility.VISIBLE -> View.VISIBLE
        Visibility.INVISIBLE -> View.INVISIBLE
        Visibility.GONE -> View.GONE
    }
}