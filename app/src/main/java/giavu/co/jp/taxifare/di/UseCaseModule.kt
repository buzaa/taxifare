package giavu.co.jp.taxifare.di

import giavu.co.jp.domain.usecase.FetchNearestSupportCityUseCase
import giavu.co.jp.domain.usecase.FetchTaxiFareUseCase
import giavu.co.jp.taxifare.map.FetchMyLocationUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @Author: Hoang Vu
 * @Date:   2019-08-21
 */
class UseCaseModule {
    val module: Module = module {
        single { FetchNearestSupportCityUseCase(api = get()) }
        single { FetchTaxiFareUseCase(api = get(), fetchNearestSupportCityUseCase = get()) }
        factory { FetchMyLocationUseCase(context = androidApplication()) }
    }
}