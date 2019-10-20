package giavu.co.jp.taxifare.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.co.jp.taxifare.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContentView(R.layout.activity_main)
    }

}
