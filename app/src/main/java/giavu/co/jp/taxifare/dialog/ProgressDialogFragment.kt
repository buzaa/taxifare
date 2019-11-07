package giavu.co.jp.taxifare.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import giavu.co.jp.taxifare.R

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-07
 */
class ProgressDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            window?.setBackgroundDrawableResource(R.color.colorTransparent)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }

        return inflater.inflate(R.layout.dialog_progress, container, false)
    }
}