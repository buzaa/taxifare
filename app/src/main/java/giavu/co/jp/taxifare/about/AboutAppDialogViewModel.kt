package giavu.co.jp.taxifare.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopify.livedataktx.MutableLiveDataKtx
import giavu.co.jp.taxifare.BuildConfig
import giavu.co.jp.taxifare.R
import jp.co.japantaxi.brooklyn.domain.resource.ResourceProvider

/**
 * @Author: Hoang Vu
 * @Date:   2019-11-09
 */
class AboutAppDialogViewModel(
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _titleMessage = MutableLiveData<String>()
    val titleMessage: LiveData<String>
        get() = _titleMessage

    private val _contentMessage = MutableLiveData<String>()
    val contentMessage: LiveData<String>
        get() = _contentMessage

    private val _closeRequest = MutableLiveDataKtx<Unit>()
    val closeRequest: LiveData<Unit>
        get() = _closeRequest

    fun apply() {
        _titleMessage.value = resourceProvider.getString(R.string.label_title)
        _contentMessage.value = resourceProvider.getString(
            R.string.label_thank,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
    }

    fun close() {
        _closeRequest.value = Unit
    }

}