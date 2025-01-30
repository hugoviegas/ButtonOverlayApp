package com.example.buttonoverlayapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.example.buttonoverlayapp.databinding.ActivityMainBinding
import com.example.buttonoverlayapp.services.ButtonListenerService
import android.app.AlertDialog
import android.net.Uri
import com.example.buttonoverlayapp.services.OverlayService
import com.example.buttonoverlayapp.ui.activities.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        setupButtons()
//        startButtonService()
//        checkOverlayPermission()
    }

    private fun setupButtons() {
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }

        binding.btnEnableAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }

    private fun startButtonService() {
        val serviceIntent = Intent(this, ButtonListenerService::class.java)
        startService(serviceIntent)
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:$packageName")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        OverlayService.hideAllOverlays()
    }

    fun checkAccessibilityService() {
        val serviceEnabled = Settings.Secure.getInt(
            contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        ) == 1

        if (!serviceEnabled) {
            AlertDialog.Builder(this)
                .setTitle("Ativar Serviço de Acessibilidade")
                .setMessage("Por favor, ative o serviço nas configurações de acessibilidade")
                .setPositiveButton("Abrir Configurações") { _, _ ->
                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                }
                .show()
        }
    }


}
