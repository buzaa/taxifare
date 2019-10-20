package giavu.co.jp.taxifare.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.extension.setOnProtectBarrageClickListener
import kotlinx.android.synthetic.main.fragment_board.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        pickup.setOnProtectBarrageClickListener {
            viewModel.setPickup()
        }
        dropoff.setOnProtectBarrageClickListener {
        }
    }

}
