package com.example.buttonoverlayapp

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.example.buttonoverlayapp.utils.OverlayManager

class ButtonOverlayAccessibilityService : AccessibilityService() {
    private val TAG = "KeyDetector"

    override fun onServiceConnected() {
        Log.d(TAG, "Serviço de acessibilidade conectado")
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        return when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    showOverlay(event.keyCode)
                }
                false // Não consome o evento, permitindo que o volume seja alterado
            }
            else -> false
        }
    }

    private fun showOverlay(keyCode: Int) {
        OverlayManager.showOverlay(this, keyCode)
        val keyName = when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> "Volume Up"
            KeyEvent.KEYCODE_VOLUME_DOWN -> "Volume Down"
            else -> "Unknown"
        }
        Log.d(TAG, "Showing overlay for $keyName")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}
}
