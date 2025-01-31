package com.example.buttonoverlayapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.example.buttonoverlayapp.R
import com.example.buttonoverlayapp.utils.OverlayManager

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            // Adicione listeners para as preferências se necessário
            findPreference<SeekBarPreference>("overlay_duration")?.setOnPreferenceChangeListener { _, newValue ->
                // Atualizar a duração do overlay
                val duration = (newValue as Int).toLong()
                OverlayManager.setOverlayDuration(duration)
                true
            }
        }
    }
}

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val prefKeys = listOf("volume_up_width", "volume_up_height", "volume_down_width", "volume_down_height")
        prefKeys.forEach { key ->
            findPreference<SeekBarPreference>(key)?.setOnPreferenceChangeListener { _, newValue ->
                // Você pode adicionar lógica adicional aqui se necessário
                true
            }
        }
    }
}

