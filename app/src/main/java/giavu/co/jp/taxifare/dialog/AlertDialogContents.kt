package giavu.co.jp.taxifare.dialog

import java.io.Serializable

data class AlertDialogContents(
    val title: String,
    val message: String,
    val buttonLabel: String? = null
) : Serializable
