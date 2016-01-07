package jp.ergo.android.screentouchdispatcher;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

/**
 * Created by ishigayawataru on 15/12/31.
 */
public class NotificationUtils {

    public static final int NOTIFICATION_ID = 1;


    public static Intent createBroadcastIntent(final Context context) {
        final Intent broadcastIntent = new Intent(context, ScreenTouchDispatcherBroadcastReceiver.class);
        final String action = ScreenTouchDispatcherBroadcastReceiver.ACTION_SWITCH;
        broadcastIntent.setAction(action);
        return broadcastIntent;
    }
    public static void removeNotification(final Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.cancel(NotificationUtils.NOTIFICATION_ID);
    }


    public static void sendNotification(final Context context, final String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        final Intent broadcastIntent = NotificationUtils.createBroadcastIntent(context);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(message);
        builder.setOngoing(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(NotificationUtils.NOTIFICATION_ID, builder.build());

    }

}
