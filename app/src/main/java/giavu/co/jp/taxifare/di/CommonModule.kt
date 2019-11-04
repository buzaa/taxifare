package giavu.co.jp.taxifare.di

import android.content.Context
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import jp.co.japantaxi.brooklyn.domain.resource.ResourceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-04
 */
class CommonModule {
    val module: Module = module {
        single<ResourceProvider> { ResourceProviderImpl(androidApplication()) }
    }
}

private class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(resId: Int): String =
        context.getString(resId)

    override fun getString(resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    override fun getColor(color: Int): Int = ContextCompat.getColor(context, color)

    override fun getDimensionPixelSize(@DimenRes resId: Int): Int =
        context.resources.getDimensionPixelSize(resId)
}