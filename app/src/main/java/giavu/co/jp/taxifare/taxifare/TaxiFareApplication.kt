package giavu.co.jp.taxifare.taxifare

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import giavu.co.jp.taxifare.BuildConfig
import giavu.co.jp.taxifare.di.dagger.ApplicationComponent
import giavu.co.jp.taxifare.di.dagger.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
open class TaxiFareApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        KoinInitializer(this).initialize()
        // DaggerApplicationComponent class is generated after build
        DaggerApplicationComponent
            .factory()
            .create(this)
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}