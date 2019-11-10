package giavu.co.jp.repository.network.core

import android.content.Context
import giavu.co.jp.repository.R
import giavu.co.jp.repository.network.retrofit.ApiAccessor

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class TaxiFareApiAccessor(private val context: Context) : ApiAccessor(context = context) {
    override fun getBaseUrl(): String {
        return context.getString(R.string.base_url)
    }

    override fun onCreateHeaders(headers: MutableMap<String, String>) {
        headers.apply {
            put(ApiHeader.CONTENT_TYPE, ApiHeader.VALUE_ACCEPT_JSON)
        }
    }

    fun from(): ApiAccessor {
        return TaxiFareApiAccessor(context)
    }
}