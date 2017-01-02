package jp.ergo.android.screentouchdispatcher

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.NotificationCompat

object NotificationUtils {

    val NOTIFICATION_ID = 1

    fun createBroadcastIntent(context: Context): Intent {
        val broadcastIntent = Intent(context, ScreenTouchDispatcherBroadcastReceiver::class.java)
        val action = ScreenTouchDispatcherBroadcastReceiver.ACTION_SWITCH
        broadcastIntent.action = action
        return broadcastIntent
    }

    fun removeNotification(context: Context) {
        val manager = NotificationManagerCompat.from(context)
        manager.cancel(NotificationUtils.NOTIFICATION_ID)
    }


    fun sendNotification(context: Context, message: String) {
        val builder = NotificationCompat.Builder(context)

        val broadcastIntent = NotificationUtils.createBroadcastIntent(context)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentText(message)
        builder.setOngoing(true)

        val manager = NotificationManagerCompat.from(context)
        manager.notify(NotificationUtils.NOTIFICATION_ID, builder.build())

    }

}
