package giavu.co.jp.taxifare.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopify.livedataktx.PublishLiveDataKtx

class AlertDialogViewModel : ViewModel() {

    private val _closeEvent: PublishLiveDataKtx<Unit> = PublishLiveDataKtx()
    val closeEvent: LiveData<Unit>
        get() = _closeEvent

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String>
        get() = _title

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message

    private val _buttonLabel: MutableLiveData<String> = MutableLiveData()
    val buttonLabel: LiveData<String>
        get() = _buttonLabel


    fun apply(dialogContents: AlertDialogContents) {
        _title.value = dialogContents.title
        _message.value = dialogContents.message
        _buttonLabel.value = dialogContents.buttonLabel
    }

    fun close() {
        _closeEvent.value = Unit
    }

}
