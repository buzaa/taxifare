package giavu.co.jp.repository.network.retrofit

import com.google.gson.GsonBuilder
import giavu.co.jp.repository.network.core.OkHttpClientFactory
import giavu.co.jp.repository.network.core.RequestFactory
import giavu.co.jp.repository.network.core.TimeoutConfigBuilder
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class ApiFactory(
        private val baseUrl: String,
        private val headerAccessor: HeaderAccessor,
        private val logging: HttpLoggingInterceptor,
        private val connectionPool: ConnectionPool) {

    fun <T> create(klass: Class<T>): T {
        return createRetrofit().create(klass)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createClient())
                .addConverterFactory(createGsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val timeoutConfig = TimeoutConfigBuilder().build()
        return OkHttpClientFactory().createInstance(connectionPool, logging,
                HeaderInterceptor(), timeoutConfig)
    }

    private fun createGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gson)
    }

    inner class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = RequestFactory(headerAccessor.get(), chain.request()).create()
            Timber.d(request.toString())
            return chain.proceed(request)
        }
    }

    interface HeaderAccessor {
        fun get(): Map<String, String>
    }

}