package jp.ergo.android.screentouchdispatcher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScreenTouchDispatcherService extends Service {

    private static final String TAG = ScreenTouchDispatcherService.class.getSimpleName();

    private View touchDispachView;

    private LinearLayout rootView;

    private ScreenTouchDispatcherView screenTouchDispatcherView;

    private DispatcherViewController dispatcherViewController;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand");
        //画面に常に表示するビューのレイアウトの設定
//        final WindowManager.LayoutParams params
//        = new WindowManager.LayoutParams(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT,
//            0, 0,
//            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT);
//
//        screenTouchDispatcherView = new ScreenTouchDispatcherView(getApplicationContext());
//
//
//
//        getWindowManager().addView(screenTouchDispatcherView, params);

        dispatcherViewController = new DispatcherViewController(getApplicationContext());
        dispatcherViewController.addToWindowManager();

        return START_STICKY;

    }

    private void setupScreenTouchDispatcherView(final Context context){
        screenTouchDispatcherView = new ScreenTouchDispatcherView(context);

    }
    
    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");

        super.onDestroy();
//        getWindowManager().removeView(touchDispachView);
            //ビューをレイヤーから削除する
        if(dispatcherViewController != null){
            dispatcherViewController.removeAll();
        }

    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private WindowManager getWindowManager(){
        return (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }
}