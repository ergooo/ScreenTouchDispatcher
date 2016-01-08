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
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        //画面に常に表示するビューのレイアウトの設定
        final WindowManager.LayoutParams params
        = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            0, 0,
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);

        screenTouchDispatcherView = new ScreenTouchDispatcherView(getApplicationContext());
        screenTouchDispatcherView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        screenTouchDispatcherView.findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        getWindowManager().addView(screenTouchDispatcherView, params);
        return START_STICKY;

    }
    
    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");

        super.onDestroy();
        getWindowManager().removeView(touchDispachView);
            //ビューをレイヤーから削除する
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