package jp.ergo.android.screentouchdispatcher

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ScreenTouchDispatcherService : Service() {

    private var dispatcherViewController: DispatcherViewController? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand")
        dispatcherViewController = DispatcherViewController(applicationContext)
        dispatcherViewController!!.addToWindowManager()

        return Service.START_STICKY

    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        //ビューをレイヤーから削除する
        if (dispatcherViewController != null) {
            dispatcherViewController!!.removeAll()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private val TAG = ScreenTouchDispatcherService::class.java.simpleName
    }
}