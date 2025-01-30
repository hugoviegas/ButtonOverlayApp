package com.example.buttonoverlayapp.services

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class ButtonListenerService : AccessibilityService() {

    private val tag = "ButtonListener"
    private var isServiceActive = false

    private fun showOverlay(buttonType: String) {
        try {
            OverlayService.showOverlay(this, buttonType)
        } catch (e: SecurityException) {
            Log.e(tag, "Permissão de overlay negada: ${e.message}")
        } catch (e: Exception) {
            Log.e(tag, "Erro ao exibir overlay: ${e.message}")
        }
    }


    override fun onServiceConnected() {
        super.onServiceConnected()
        isServiceActive = true
        Log.i(tag, "Serviço inicializado com sucesso")
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (!isServiceActive) return false

        return when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> handleKey(event, "volume_up")
            KeyEvent.KEYCODE_VOLUME_DOWN -> handleKey(event, "volume_down")
            KeyEvent.KEYCODE_POWER -> handleKey(event, "power")
            else -> super.onKeyEvent(event)
        }
    }

    private fun handleKey(event: KeyEvent, type: String): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            showOverlay(type)
            return true
        }
        return false
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Mantido para logs
    }

    override fun onInterrupt() {
        Log.w(tag, "Serviço interrompido")
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceActive = false
        Log.i(tag, "Serviço finalizado")
    }
}
