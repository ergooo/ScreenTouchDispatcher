package jp.ergo.android.screentouchdispatcher

import android.app.Service
import android.content.Intent
import android.os.IBinder
import jp.ergo.android.screentouchdispatcher.util.Logger

/**
 * WindowManagerに載せるViewのコントローラと
 * Notificationのコントロールを司るサービス
 */
class ScreenTouchDispatcherService : Service() {

    private var dispatcherViewController: DispatcherViewController? = null

    override fun onCreate() {
        super.onCreate()
        Logger.d(TAG, "onCreate")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Logger.d(TAG, "onStartCommand")
        Logger.d(TAG, "flags: " + flags)
        Logger.d(TAG, "startId: " + startId)
        Logger.d(TAG, "intent: " + intent)
        Logger.d(TAG, "dispatcherViewController: " + dispatcherViewController)
        intent?.let {
            when (it.action) {
                ACTION_START -> {
                    when (dispatcherViewController) {
                        null -> {
                            dispatcherViewController = DispatcherViewController(this)
                            dispatcherViewController?.let {
                                it.onDispachStateChanged = { checked ->
                                    updateNotification(checked)
                                }
                                it.addToWindowManager()
                                val notification = NotificationUtils.createNotification(this@ScreenTouchDispatcherService, createNotificationMessage(it.isDispatching()))
                                startForeground(NotificationUtils.NOTIFICATION_ID, notification)
                            }

                        }
                        else -> {
                        }
                    }
                }
                ACTION_STOP -> {
                    stopSelf()
                }
                ACTION_SWITCH -> {
                    dispatcherViewController?.let {
                        it.setDispaching(!it.isDispatching())
                    }
                }
                else -> {
                }
            }
        }

        return Service.START_STICKY
    }

    private fun updateNotification(isDispatching: Boolean) {
        NotificationUtils.sendNotification(this, createNotificationMessage(isDispatching))
    }

    private fun createNotificationMessage(isDispatching: Boolean): String {
        return if (isDispatching) {
            "タッチしてロックを解除"
        } else {
            "タッチして画面をロック"
        }
    }

    override fun onDestroy() {
        Logger.d(TAG, "onDestroy")
        super.onDestroy()

        NotificationUtils.removeNotification(this)
        dispatcherViewController?.removeAll()
        dispatcherViewController = null
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private val TAG = ScreenTouchDispatcherService::class.java.simpleName
        val ACTION_START = ScreenTouchDispatcherService::class.java.simpleName + ".ACTION_START"
        val ACTION_STOP = ScreenTouchDispatcherService::class.java.simpleName + ".ACTION_STOP"
        val ACTION_SWITCH = ScreenTouchDispatcherService::class.java.simpleName + ".ACTION_SWITCH"
    }
}