package giavu.co.jp.taxifare.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shopify.livedataktx.PublishLiveDataKtx

class AlertDialogViewModel : ViewModel() {

    private val _clickEvent: PublishLiveDataKtx<Unit> = PublishLiveDataKtx()
    private val _closeEvent: PublishLiveDataKtx<Unit> = PublishLiveDataKtx()
    private val _dismissEvent: PublishLiveDataKtx<Unit> = PublishLiveDataKtx()

    val clickEvent: LiveData<Unit>
        get() = _clickEvent

    val closeEvent: LiveData<Unit>
        get() = _closeEvent

    val dismissEvent: LiveData<Unit>
        get() = _dismissEvent

    fun notifyClickEvent() {
        _clickEvent.value = Unit
    }

    fun notifyCloseEvent() {
        _closeEvent.value = Unit
    }

    fun notifyDismissEvent() {
        _dismissEvent.value = Unit
    }
}
