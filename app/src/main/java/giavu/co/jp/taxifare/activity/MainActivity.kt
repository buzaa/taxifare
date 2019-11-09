package giavu.co.jp.taxifare.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.databinding.ActivityMainBindingImpl
import giavu.co.jp.taxifare.result.ResultActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        fun startWithoutStack(activity: Activity) {
            Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }.let { intent ->
                activity.startActivity(intent)
            }
        }
    }

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

            currentLocationRequest.observe(this@MainActivity, Observer {
                requestPermissionIfNeeds()
            })
        }
    }

    private fun requestPermissionIfNeeds() {
        val permissionResultFineLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionResultFineLocation == PackageManager.PERMISSION_GRANTED) {
            mainViewModel.moveMyLocation()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }
    }

}
