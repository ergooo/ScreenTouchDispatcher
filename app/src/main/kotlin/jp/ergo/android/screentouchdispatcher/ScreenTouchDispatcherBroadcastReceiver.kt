package jp.ergo.android.screentouchdispatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.Toast
import jp.ergo.android.screentouchdispatcher.util.Logger

class ScreenTouchDispatcherBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Logger.d(TAG, "onReceive() " + intent.action)
        Toast.makeText(context, "受け取ったよ", Toast.LENGTH_LONG).show()

        val action = intent.action
        val serviceIntent = Intent(context, ScreenTouchDispatcherService::class.java)
        if (action == ACTION_SWITCH) {
            val isDispatching = isDispatching(context)
            Logger.d(TAG, "isDispatching: " + isDispatching)
            setDispatching(context, !isDispatching)
            if (isDispatching) {
                context.stopService(serviceIntent)
                NotificationUtils.sendNotification(context, "タッチして画面をロック")
            } else {
                context.startService(serviceIntent)
                NotificationUtils.sendNotification(context, "タッチしてロックを解除")
            }
        }
    }


    private fun isDispatching(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isDispatching", false)
    }

    private fun setDispatching(context: Context, isDispatching: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isDispatching", isDispatching).commit()

    }

    companion object {

        private val TAG = ScreenTouchDispatcherBroadcastReceiver::class.java.simpleName

        val ACTION_START_SERVICE = "jp.ergo.android.screentouchdispatcher.SATRT_SERVICE"
        val ACTION_STOP_SERVICE = "jp.ergo.android.screentouchdispatcher.STOP_SERVICE"
        val ACTION_SWITCH = "jp.ergo.android.screentouchdispatcher.SWITCH"
    }

}