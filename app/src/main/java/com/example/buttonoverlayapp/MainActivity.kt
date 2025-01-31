package com.example.buttonoverlayapp;

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.buttonoverlayapp.ui.activities.SettingsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"))
            startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
        }

        val btnEnableService = findViewById<Button>(R.id.btnEnableAccessibility)
        btnEnableService.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }
    companion object {
        private const val REQUEST_CODE_OVERLAY_PERMISSION = 1234
    }
    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }
}
