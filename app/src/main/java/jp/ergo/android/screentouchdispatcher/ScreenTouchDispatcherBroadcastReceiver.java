package jp.ergo.android.screentouchdispatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ScreenTouchDispatcherBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = ScreenTouchDispatcherBroadcastReceiver.class.getSimpleName();

    public static final String ACTION_START_SERVICE = "jp.ergo.android.screentouchdispatcher.SATRT_SERVICE";
    public static final String ACTION_STOP_SERVICE = "jp.ergo.android.screentouchdispatcher.STOP_SERVICE";
    public static final String ACTION_SWITCH = "jp.ergo.android.screentouchdispatcher.SWITCH";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() " + intent.getAction());
        Toast.makeText(context, "受け取ったよ", Toast.LENGTH_LONG).show();

        final String action = intent.getAction();
        final Intent serviceIntent = new Intent(context, ScreenTouchDispatcherService.class);
        if (action.equals(ACTION_SWITCH)) {
            final boolean isDispatching = isDispatching(context);
            Log.d(TAG, "isDispatching: " + isDispatching);
            setDispatching(context, !isDispatching);
            if (isDispatching) {
                context.stopService(serviceIntent);
                NotificationUtils.sendNotification(context, "タッチして画面をロック");
            } else {
                context.startService(serviceIntent);
                NotificationUtils.sendNotification(context, "タッチしてロックを解除");

            }
        }
    }


    private boolean isDispatching(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isDispatching", false);
    }

    private void setDispatching(final Context context, final boolean isDispatching) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("isDispatching", isDispatching).commit();

    }

}