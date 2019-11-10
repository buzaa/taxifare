package giavu.co.jp.taxifare.extension

import androidx.fragment.app.FragmentActivity
import giavu.co.jp.taxifare.dialog.ProgressDialogFragment
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-07
 */

fun FragmentActivity.showProgress() {
    Timber.d("Show")
    val tag = ProgressDialogFragment::class.java.name
    val fragment: ProgressDialogFragment? =
        supportFragmentManager.findFragmentByTag(tag) as ProgressDialogFragment?
    if (fragment != null) {
        return
    }
    ProgressDialogFragment().showNow(supportFragmentManager, tag)
}

fun FragmentActivity.hideProgress() {
    Timber.d("Dismiss")
    val fragment: ProgressDialogFragment? =
        supportFragmentManager.findFragmentByTag(ProgressDialogFragment::class.java.name) as ProgressDialogFragment?
    fragment?.dismissAllowingStateLoss()

}