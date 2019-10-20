package giavu.co.jp.taxifare.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.databinding.FragmentBoardBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-20
 */
class BoardFragment : Fragment() {

    private val viewModel: BoardViewModel by sharedViewModel()

    private lateinit var dataBinding: FragmentBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentBoardBinding>(
            inflater,
            R.layout.fragment_board,
            container,
            false
        ).apply {
            viewModel = this@BoardFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return dataBinding.root
    }
}