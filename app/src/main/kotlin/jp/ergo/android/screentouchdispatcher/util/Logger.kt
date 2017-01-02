package jp.ergo.android.screentouchdispatcher.util

import android.os.Debug
import android.util.Log

import jp.ergo.android.screentouchdispatcher.BuildConfig

object Logger {

    //    private static final String TAG = "MyApplication";
    private val TAG = "ScreenTouchDispatcher"

    fun d(tag: String = TAG, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun e(tag: String = TAG, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
    }

    fun i(tag: String = TAG, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun v(tag:String = TAG, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun w(tag:String = TAG, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg)
        }
    }

    @JvmOverloads fun heap(tag: String = TAG) {
        if (BuildConfig.DEBUG) {
            val msg = "heap : Free=" + java.lang.Long.toString(Debug.getNativeHeapFreeSize() / 1024) + "kb" +
                    ", Allocated=" + java.lang.Long.toString(Debug.getNativeHeapAllocatedSize() / 1024) + "kb" +
                    ", Size=" + java.lang.Long.toString(Debug.getNativeHeapSize() / 1024) + "kb"

            Log.v(tag, msg)
        }
    }
}
