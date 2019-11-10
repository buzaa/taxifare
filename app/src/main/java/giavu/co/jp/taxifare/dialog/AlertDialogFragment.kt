package giavu.co.jp.taxifare.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.databinding.LayoutDialogAlertFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class AlertDialogFragment : DialogFragment() {

    companion object {
        private const val KEY_CONTENTS = "key-contents"

        fun newInstance(contents: AlertDialogContents): DialogFragment {
            return AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_CONTENTS, contents)
                }
            }
        }
    }

    private val viewModel: AlertDialogViewModel by viewModel()

    private val contents: AlertDialogContents by lazy {
        arguments?.getSerializable(KEY_CONTENTS) as AlertDialogContents
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<LayoutDialogAlertFragmentBinding>(
                inflater,
                R.layout.layout_dialog_alert_fragment,
                container,
                false
        ).apply {
            lifecycleOwner = this@AlertDialogFragment
            viewModel = this@AlertDialogFragment.viewModel
            this.title.text = contents.title
            this.message.text = contents.message
            this.positiveButton.text = "OK"
            this.negativeButton.text = "Cancel"

        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.clickEvent.observe(viewLifecycleOwner, Observer {
            dismiss()
        })
    }
}