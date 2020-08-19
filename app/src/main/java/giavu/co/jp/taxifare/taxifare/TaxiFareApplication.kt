package giavu.co.jp.taxifare.taxifare

import android.app.Application
import giavu.co.jp.taxifare.BuildConfig
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
open class TaxiFareApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        KoinInitializer(this).initialize()

    }
}