package jp.ergo.android.screentouchdispatcher

import android.content.Context
import android.view.MotionEvent
import android.widget.Button
import java.util.concurrent.atomic.AtomicBoolean


class Switch(context: Context) : Button(context) {
    var onCheckChanged: ((Boolean) -> Unit)? = null
    var onDragging: ((Int, Int) -> Unit)? = null
    private val startPosition = Position()

    private val isChecked = AtomicBoolean(false)

    init {
        setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPosition.update(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isMoving(startPosition, motionEvent)) {
                        onDragging?.invoke(motionEvent.rawX.toInt() - (view.width / 2), motionEvent.rawY.toInt() - (view.height))
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (isMoving(startPosition, motionEvent)) {
                        startPosition.update(0, 0)

                    } else {
                        isChecked.set(!isChecked())
                        onCheckChanged?.invoke(isChecked())
                    }
                }
            }
            false
        }
    }

    private fun isMoving(startPosition: Position, motionEvent: MotionEvent): Boolean {
        return Math.abs(startPosition.x - motionEvent.rawX.toInt()) > 20 || Math.abs(startPosition.y - motionEvent.rawY.toInt()) > 20
    }

    fun isChecked(): Boolean {
        return isChecked.get()
    }

    fun setChecked(isChecked: Boolean) {
        this.isChecked.set(isChecked)
    }
}