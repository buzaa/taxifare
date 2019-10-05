package giavu.co.jp.taxifare.di

import giavu.co.jp.repository.network.core.TaxiFareApiAccessor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class ApiModule {

    val module: Module = org.koin.dsl.module.module {
        single { TaxiFareApiAccessor(androidApplication()).from() }
    }
}