package giavu.co.jp.taxifare.di

import giavu.co.jp.repository.network.api.TaxiFareApi
import giavu.co.jp.repository.network.core.TaxiFareApiAccessor
import giavu.co.jp.repository.network.retrofit.ApiAccessor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class ApiModule {

    val module: Module = module {
        single { TaxiFareApiAccessor(androidApplication()).from() }
        single { get<ApiAccessor>().using(TaxiFareApi::class.java) }
    }
}