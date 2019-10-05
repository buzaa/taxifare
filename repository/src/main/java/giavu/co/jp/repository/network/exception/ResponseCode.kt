package giavu.co.jp.repository.network.exception

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/20
 */
data class ResponseCode(
        @SerializedName("error_code") val errorCode: String,
        @SerializedName("message") val messageError: String
): Exception()