package jp.ergo.android.screentouchdispatcher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by ishigayawataru on 16/01/08.
 */
public class ScreenTouchDispatcherView extends FrameLayout {


    public ScreenTouchDispatcherView(Context context) {
        super(context, null);
    }

    public ScreenTouchDispatcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.screen_touch_dispatch_view, this));
    }
}
