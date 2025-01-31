package com.example.buttonoverlayapp.services

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.util.Log
import com.example.buttonoverlayapp.utils.OverlayView

object OverlayService {
    private var overlayView: OverlayView? = null
    private var windowManager: WindowManager? = null
    private const val TAG = "OverlayService"

    fun showOverlay(context: Context, buttonType: String) {
        try {
            if (windowManager == null) {
                windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            }

            val params = WindowManager.LayoutParams().apply {
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    WindowManager.LayoutParams.TYPE_PHONE
                }
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                format = PixelFormat.TRANSLUCENT
                gravity = Gravity.START or Gravity.TOP
            }

            hideAllOverlays()

            overlayView = OverlayView(context)

            windowManager?.addView(overlayView, params)
            Log.d(TAG, "Overlay exibido para o botão: $buttonType")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao exibir overlay: ${e.message}")
        }
    }

    fun hideAllOverlays() {
        try {
            overlayView?.let { view ->
                windowManager?.removeView(view)
                overlayView = null
                Log.d(TAG, "Overlay removido")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao remover overlay: ${e.message}")
        }
    }

    fun startService(requireContext: Context) {

    }

    fun stopService(requireContext: Context) {

    }

    fun setOverlayDuration(duration: Int) {

    }
}
