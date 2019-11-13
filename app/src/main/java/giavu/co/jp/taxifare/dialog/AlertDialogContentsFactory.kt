package giavu.co.jp.taxifare.dialog

import com.google.gson.Gson
import giavu.co.jp.repository.network.exception.ResponseCode

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-14
 */
object AlertDialogContentsFactory {
    fun create(throwable: Throwable): AlertDialogContents {
        val message = throwable.message ?: "HTTP FAILED"
        return when {
            message.contains("error_code") -> {
                val response = Gson().fromJson(message, ResponseCode::class.java)
                AlertDialogContents(
                    title = response.messageError,
                    message = response.errorCode,
                    buttonLabel = "Close"
                )
            }
            message.contains("HTTP FAILED") -> {
                AlertDialogContents(
                    title = "Network error",
                    message = "Please check your network connection and try again !",
                    buttonLabel = "Close"
                )
            }
            else -> {
                AlertDialogContents(
                    title = "Network error",
                    message = "Please check your network connection and try again !",
                    buttonLabel = "Close"
                )
            }
        }
    }
}