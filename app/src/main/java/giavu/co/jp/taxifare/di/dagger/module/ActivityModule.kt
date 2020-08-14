package giavu.co.jp.taxifare.di.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import giavu.co.jp.taxifare.activity.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}