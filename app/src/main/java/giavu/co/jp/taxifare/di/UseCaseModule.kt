package giavu.co.jp.taxifare.di

import giavu.co.jp.domain.usecase.FetchNearestSupportCity
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2019-08-21
 */
class UseCaseModule {
    val module: Module = org.koin.dsl.module.module {
        single { FetchNearestSupportCity(api = get()) }
    }
}