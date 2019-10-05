package giavu.co.jp.taxifare.map

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment

/**
 * @Author: Hoang Vu
 * @Date:   2019-10-05
 */
class MapFragment: SupportMapFragment() {
    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
    }

    override fun onActivityCreated(p0: Bundle?) {
        super.onActivityCreated(p0)
    }

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
    }

    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        return super.onCreateView(p0, p1, p2)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}