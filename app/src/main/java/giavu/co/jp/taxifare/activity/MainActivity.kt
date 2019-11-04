package giavu.co.jp.taxifare.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.databinding.ActivityMainBindingImpl
import giavu.co.jp.taxifare.result.ResultActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var viewDataBinding: ActivityMainBindingImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        initDataBinding()
        observeMainViewModel()
    }

    private fun initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<ActivityMainBindingImpl>(
            this, R.layout.activity_main
        ).apply {
            viewModel = this@MainActivity.mainViewModel
            lifecycleOwner = this@MainActivity
        }
    }

    private fun observeMainViewModel() {
        with(mainViewModel) {
            calculateRequest.observe(this@MainActivity, Observer {
                startActivity(
                    ResultActivity.createIntent(
                        this@MainActivity,
                        pickupLocation = it.pickup,
                        dropoffLocation = it.dropoff
                    )
                )
            })
        }
    }

}
