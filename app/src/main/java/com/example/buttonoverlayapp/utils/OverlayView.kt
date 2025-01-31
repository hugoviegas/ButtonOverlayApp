package com.example.buttonoverlayapp.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class OverlayView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    var overlayWidth = 100
    var overlayHeight = 100
    var distanceFromRight = 0
    var distanceFromTop = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val right = width - distanceFromRight.toFloat()
        val left = right - overlayWidth
        val top = distanceFromTop.toFloat()
        val bottom = top + overlayHeight
        canvas.drawRoundRect(left, top, right, bottom, 20f, 20f, paint)
    }
}

