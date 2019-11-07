package giavu.co.jp.taxifare.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import giavu.co.jp.domain.model.Location
import giavu.co.jp.taxifare.R
import giavu.co.jp.taxifare.activity.MainActivity
import giavu.co.jp.taxifare.databinding.ActivityResultBinding
import giavu.co.jp.taxifare.extension.hideProgress
import giavu.co.jp.taxifare.extension.showProgress
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.reactivestreams.Publisher
import java.io.Serializable

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val KEY_ARGUMENT = "key_argument"

        fun createIntent(
            context: Context,
            pickupLocation: Location.Coordinate,
            dropoffLocation: Location.Coordinate
        ): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(
                    KEY_ARGUMENT,
                    Argument(
                        pickupLocation = pickupLocation,
                        dropoffLocation = dropoffLocation
                    )
                )
            }
        }
    }

    private lateinit var viewDataBinding: ActivityResultBinding
    private val resultViewModel: ResultViewModel by viewModel()

    private data class Argument(
        val pickupLocation: Location.Coordinate,
        val dropoffLocation: Location.Coordinate
    ) : Serializable

    private val argument: Argument by lazy {
        intent.getSerializableExtra(KEY_ARGUMENT) as Argument
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        initialize()
    }

    private fun initialize() {
        initDataBinding()
        observeViewModel()
    }

    private fun initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<ActivityResultBinding>(
            this, R.layout.activity_result
        ).apply {
            viewModel = this@ResultActivity.resultViewModel.apply {
                fetchMyLocation()
            }
            lifecycleOwner = this@ResultActivity
        }
    }

    private fun observeViewModel() {
        with(resultViewModel) {
            mapInitialized.observe(this@ResultActivity, Observer {
                addMarker(
                    resourceId = R.drawable.ic_start,
                    location = LatLng(argument.pickupLocation.lat, argument.pickupLocation.lon)
                )

                addMarker(
                    resourceId = R.drawable.ic_goal,
                    location = LatLng(argument.dropoffLocation.lat, argument.dropoffLocation.lon)
                )
                // Calculate taxi fare
                fetchTaxiFare(pickup = argument.pickupLocation, dropoff = argument.dropoffLocation)
            })
            myLocation.observe(this@ResultActivity, Observer {
                /*loadMapLocation(
                    LatLng(
                        argument.dropoffLocation.lat,
                        argument.dropoffLocation.lon
                    ), it
                )*/
                loadMapLocation(
                    LatLng(
                        argument.pickupLocation.lat,
                        argument.pickupLocation.lon
                    ),
                    LatLng(
                        argument.dropoffLocation.lat,
                        argument.dropoffLocation.lon
                    )
                )
            })

            setPublisherMapTopPadding(
                owner = this@ResultActivity,
                publisherMapTopPadding = getPublisherLayoutBottomY()
            )

            showProgressRequest.observe(this@ResultActivity, Observer {
                showProgress()
            })

            hideProgressRequest.observe(this@ResultActivity, Observer {
                hideProgress()
            })
        }
    }

    private fun getPublisherLayoutBottomY(): Publisher<Int> {
        return Publisher {
            viewDataBinding.layoutResult.doOnPreDraw { layout ->
                it.onNext((layout.y + layout.height).toInt())
                it.onComplete()
            }
        }
    }

    override fun onBackPressed() {
        MainActivity.startWithoutStack(this)
    }
}
