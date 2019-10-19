package giavu.co.jp.taxifare.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.extension.setOnProtectBarrageClickListener
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        pickup.setOnProtectBarrageClickListener {
            Timber.d("Pickup location is :%s", viewModel.centerLocation.value.toString())
        }
        dropoff.setOnProtectBarrageClickListener {
            Timber.d("Drop-off location is :%s", viewModel.centerLocation.value.toString())
        }
    }

}
