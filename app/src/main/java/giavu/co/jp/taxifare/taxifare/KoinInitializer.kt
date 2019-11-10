package giavu.co.jp.taxifare.taxifare

import android.app.Application
import giavu.co.jp.taxifare.BuildConfig
import giavu.co.jp.taxifare.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
class KoinInitializer(private val application: Application) {

    fun initialize() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            } else {
                EmptyLogger()
            }
            androidContext(androidContext = application)
            modules(Modules().get())
        }
    }
}