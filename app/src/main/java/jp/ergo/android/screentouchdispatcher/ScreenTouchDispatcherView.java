package jp.ergo.android.screentouchdispatcher;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class ScreenTouchDispatcherView extends FrameLayout {

    private final FloatingActionButton floatingActionButton;

    public ScreenTouchDispatcherView(Context context) {
        this(context, null);
    }

    public ScreenTouchDispatcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.screen_touch_dispatch_view, this);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.GONE);
            }
        });

    }
}
