//package jp.ergo.android.screentouchdispatcher;
//
//import android.content.Context;
//import android.graphics.PixelFormat;
//import android.support.design.widget.FloatingActionButton;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.WindowManager;
//
//public class DispatcherViewController {
//    private final View dispatchView;
//    private final FloatingActionButton switcherButton;
//    private final WindowManager windowManager;
//
//    private boolean isDispatching;
//
//    public DispatcherViewController(final Context context) {
//        dispatchView = new View(context);
//        this.switcherButton = (FloatingActionButton) LayoutInflater.from(context).inflate(R.layout.floating_action_button,null);
////        this.switcherButton = new FloatingActionButton(context);
//        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//
//        switcherButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSwitcherButtonClicked();
//            }
//        });
//
//        dispatchView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//    }
//
//    public void addToWindowManager() {
//        addSwitcherButton();
//    }
//
//    public void removeAll() {
//        if (isDispatching) {
//            stopDispatching();
//        }
//
//        windowManager.removeView(switcherButton);
//
//    }
//
//    private void updateSwitcherButton() {
//        final WindowManager.LayoutParams params = createSwitcherButtonParams();
//        windowManager.updateViewLayout(switcherButton, params);
//    }
//
//    private void addDispatchView() {
//        final WindowManager.LayoutParams params
//                = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT,
//                0, 0,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//        windowManager.addView(dispatchView, params);
//    }
//
//    private void addSwitcherButton() {
//        final WindowManager.LayoutParams params = createSwitcherButtonParams();
//        windowManager.addView(switcherButton, params);
//    }
//
//    private WindowManager.LayoutParams createSwitcherButtonParams() {
//        return new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                0, 0,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//    }
//
//
//    private void startDispatching() {
//
//
//        addDispatchView();
//
//        windowManager.removeView(switcherButton);
//        addSwitcherButton();
//
//
//        isDispatching = true;
//
//    }
//
//    private void stopDispatching() {
//        isDispatching = false;
//
//        windowManager.removeView(dispatchView);
//
//    }
//
//    private void onSwitcherButtonClicked() {
//        if (isDispatching) {
//            stopDispatching();
//        } else {
//            startDispatching();
//        }
//    }
//
//}
