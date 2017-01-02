package jp.ergo.android.screentouchdispatcher

import android.content.Context
import android.view.MotionEvent.*
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.fluid_floating_action_button.view.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ドラッグアンドドロップで動かすことのできるViewを持つもの
 */
class FluidView(context: Context) : FrameLayout(context) {
    private val TAG = FluidView::class.java.simpleName

    private var onPositionChangedListener: ((Float, Float) -> Unit)? = null

    private val isButtonPressed = AtomicBoolean()

    init {
        val root = inflate(context, R.layout.fluid_floating_action_button, this)

        floatingButton.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                ACTION_DOWN -> isButtonPressed.set(true)
            }
            false
        }

        root.setOnTouchListener { view, motionEvent ->
            when (isButtonPressed.get()) {
                true -> {
                    when (motionEvent.action) {
                        ACTION_MOVE -> {
                            floatingButton.x = calcPoint(floatingButton.width, root.width, motionEvent.x)
                            floatingButton.y = calcPoint(floatingButton.height, root.height, motionEvent.y)
                        }
                        ACTION_UP -> {
                            isButtonPressed.set(false)
                            onPositionChangedListener?.invoke(floatingButton.x, floatingButton.y)

                        }
                    }
                    true
                }
                false -> false
            }
        }
    }

    private fun calcPoint(childLength: Int, parentLength: Int, touchPoint: Float): Float {
        val point = touchPoint - childLength / 2
        return when {
            (point + childLength > parentLength) -> (parentLength - childLength).toFloat()
            (point < 0) -> 0.0f
            else -> point
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        floatingButton.setOnClickListener(l)
    }

    override fun setBackgroundColor(color: Int) {
        floatingButton.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resid: Int) {
        floatingButton.setBackgroundResource(resid)
    }
}


