package jp.ergo.android.screentouchdispatcher

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager

data class Position(var x: Int = 0, var y: Int = 0) {
    fun update(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}

class DispatcherViewController(context: Context) {
    private val TAG = DispatcherViewController::class.java.simpleName
    private val LAYOUT_TYPE = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

    private val dispatchView: View = View(context)
    private val switch: Switch = Switch(context)
    private val windowManager: WindowManager

    private val switchPosition = Position()

    init {
        this.windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        switch.onCheckChanged = { isChecked ->
            when {
                isChecked -> showDispatchView()
                else -> dismissDispatchView()
            }
        }

        switch.onDragging = { x, y ->
            updateSwitch(x, y)
        }
    }

    private fun updateSwitch(x: Int, y: Int) {
        val params = createSwitchParams(x, y)
        windowManager.updateViewLayout(switch, params)
    }

    private fun showDispatchView() {
        dispatchView.visibility = VISIBLE
    }

    private fun dismissDispatchView() {
        dispatchView.visibility = GONE
    }

    fun addToWindowManager() {
        addDispatchView()
        addSwitch()
        dismissDispatchView()
    }

    fun removeAll() {
        if (dispatchView.isShown) {
            windowManager.removeView(dispatchView)
        }
        if (switch.isShown) {
            windowManager.removeView(switch)
        }
    }

    private fun addDispatchView() {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0, 0,
                LAYOUT_TYPE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)
        windowManager.addView(dispatchView, params)
    }

    private fun addSwitch() {
        val params = createSwitchParams(switchPosition.x, switchPosition.y)
        windowManager.addView(switch, params)
    }

    private fun createSwitchParams(x: Int, y: Int): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                x, y,
                LAYOUT_TYPE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)
        params.gravity = (Gravity.START or Gravity.TOP)
        return params
    }

}
