package giavu.co.jp.repository.network.retrofit

import android.content.Context
import okhttp3.ConnectionPool
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
abstract class ApiAccessor(private val context: Context): ApiFactory.HeaderAccessor{
    private val connectionPool = ConnectionPool()
    private val interceptor = createLoggingInterceptor()

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor(LoggerImpl())
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    internal abstract fun getBaseUrl(): String
    internal abstract fun onCreateHeaders(headers: MutableMap<String, String>)

    fun <T> using(klass: Class<T>): T {
        val factory = ApiFactory(
            getBaseUrl(),
            this,
            interceptor,
            connectionPool
        )
        return factory.create(klass)
    }

    private class LoggerImpl : HttpLoggingInterceptor.Logger {

        override fun log(message: String) {
            Timber.d(message)
        }
    }

    override fun get(): Map<String, String> {
        val headers = HashMap<String, String>()
        onCreateHeaders(headers)
        return headers

    }
}