package giavu.co.jp.taxifare.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shopify.livedataktx.PublishLiveDataKtx

class AlertDialogViewModel : ViewModel() {

    private val _closeEvent: PublishLiveDataKtx<Unit> = PublishLiveDataKtx()
    val closeEvent: LiveData<Unit>
        get() = _closeEvent

    fun close() {
        _closeEvent.value = Unit
    }

}
