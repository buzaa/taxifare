package giavu.co.jp.repository.network.core

import okhttp3.Headers
import okhttp3.Request


/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class RequestFactory(private val headers: Map<String, String>, private val request: Request) {


    fun create(): Request {
        val builder = request.newBuilder()
        builder.headers(Headers.of(headers))

        return builder.build()
    }


}
