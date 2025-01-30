package com.example.buttonoverlayapp.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.buttonoverlayapp.R

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_overlay, this)
    }

    fun setButtonType(buttonType: String) {
        when (buttonType) {
            "volume_up" -> setBackgroundResource(R.drawable.volume_up_overlay)
            "volume_down" -> setBackgroundResource(R.drawable.volume_down_overlay)
            "power" -> setBackgroundResource(R.drawable.power_button_overlay)
        }
    }

    fun setPosition(position: Pair<Int, Int>) {
        translationX = position.first.toFloat()
        translationY = position.second.toFloat()
    }
}
