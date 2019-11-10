package giavu.co.jp.taxifare.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.databinding.FragmentAboutAppBinding
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-09
 */
class AboutAppDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(): DialogFragment = AboutAppDialogFragment()
    }

    private val dialogViewModel: AboutAppDialogViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Alert_RoundedCorners_White)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentAboutAppBinding>(
            inflater,
            R.layout.fragment_about_app,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AboutAppDialogFragment.dialogViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogViewModel.apply()
        observeDialogViewModel()
    }

    private fun observeDialogViewModel() {
        with(dialogViewModel) {
            closeRequest.observe(viewLifecycleOwner, Observer {
                dismiss()
            })
        }
    }
}