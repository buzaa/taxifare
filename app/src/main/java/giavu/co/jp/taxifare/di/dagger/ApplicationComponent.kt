package giavu.co.jp.taxifare.di.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import giavu.co.jp.taxifare.di.dagger.module.ActivityModule
import giavu.co.jp.taxifare.taxifare.TaxiFareApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (ActivityModule::class)
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }

    fun inject(application: TaxiFareApplication)
}