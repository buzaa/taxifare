package giavu.co.jp.taxifare.di

import org.koin.core.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
class Modules {
    fun get(): List<Module> = listOf(
        ApiModule().module,
        ViewModelModule().module,
        UseCaseModule().module,
        CommonModule().module
    )
}
