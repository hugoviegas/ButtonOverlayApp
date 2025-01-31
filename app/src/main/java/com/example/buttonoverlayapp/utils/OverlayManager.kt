package com.example.buttonoverlayapp.utils

import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import androidx.preference.PreferenceManager

object OverlayManager {
    private var windowManager: WindowManager? = null
    private var overlayView: OverlayView? = null
    private var overlayDuration: Long = 500 // Valor padrão

    fun setOverlayDuration(duration: Long) {
        overlayDuration = duration
    }

    fun showOverlay(context: Context, keyCode: Int) {
        if (windowManager == null) {
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, // Adiciona esta flag
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
            x = 0 // Isso garante que o overlay fique na extremidade direita
        }

        overlayView = OverlayView(context).apply {
            overlayWidth = getOverlayWidth(context, keyCode)
            overlayHeight = getOverlayHeight(context, keyCode)
            distanceFromTop = getOverlayPosition(context, keyCode)
        }

        windowManager?.addView(overlayView, params)

        Handler(Looper.getMainLooper()).postDelayed({
            removeOverlay()
        }, overlayDuration)
    }

    private fun removeOverlay() {
        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
        }
    }

    private fun getOverlayWidth(context: Context, keyCode: Int): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val key = if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) "volume_up_width" else "volume_down_width"
        return prefs.getInt(key, 50)
    }

    private fun getOverlayHeight(context: Context, keyCode: Int): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val key = if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) "volume_up_height" else "volume_down_height"
        return prefs.getInt(key, 100)
    }

    private fun getOverlayPosition(context: Context, keyCode: Int): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val key = if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) "volume_up_position" else "volume_down_position"
        return prefs.getInt(key, 50)
    }


}
