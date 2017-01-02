package jp.ergo.android.screentouchdispatcher;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class FluidFloatingActionButtonTestActivity extends AppCompatActivity {

    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(new LinearLayout(this));

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(new FluidView(this), createSwitcherButtonParams());

    }
    private WindowManager.LayoutParams createSwitcherButtonParams() {
        return new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
    }


}
