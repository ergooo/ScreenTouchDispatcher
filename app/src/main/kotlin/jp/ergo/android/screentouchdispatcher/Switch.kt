package jp.ergo.android.screentouchdispatcher

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.MotionEvent
import android.widget.ImageButton


class Switch(context: Context) : ImageButton(context) {
    var onCheckChanged: ((Boolean) -> Unit)? = null
    var onDragging: ((Int, Int) -> Unit)? = null
    private val startPosition = Position()

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.switch_button, null)
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
                        setChecked(!isChecked())
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
        return isActivated
    }

    fun setChecked(isChecked: Boolean) {
       isActivated = isChecked
    }
}