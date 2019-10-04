package giavu.co.jp.taxifare.fragment

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.m4b.maps.SupportMapFragment

/**
 * @Author: Hoang Vu
 * @Date:   2019-05-31
 */
class MapFragment : SupportMapFragment() {

    companion object{
        private val TAG: String = MapFragment.javaClass.simpleName
    }

    override fun onCreate(p0: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(p0)
    }

    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return super.onCreateView(p0, p1, p2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(p0: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(p0)
    }

    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        Log.d(TAG, "onInflate")
        super.onInflate(context, attrs, savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }
}