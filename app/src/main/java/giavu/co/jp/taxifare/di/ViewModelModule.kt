package giavu.co.jp.taxifare.di

import giavu.co.jp.taxifare.activity.MainViewModel
import giavu.co.jp.taxifare.result.BoardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
class ViewModelModule {

    val module: Module = module {
        viewModel {
            MainViewModel(
                application = androidApplication(),
                fetchMyLocationUseCase = get(),
                fetchNearestSupportCityUseCase = get()
            )
        }
        viewModel {
            BoardViewModel()
        }
    }
}
