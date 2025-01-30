package com.example.buttonoverlayapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.buttonoverlayapp.R
import com.example.buttonoverlayapp.services.OverlayService

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Configuração para ajuste de posição do overlay
        findPreference<Preference>("overlay_position")?.setOnPreferenceClickListener {
            // Aqui você pode iniciar uma nova Activity para ajuste de posição
            // Por exemplo:
            // startActivity(Intent(activity, PositionAdjustActivity::class.java))
            true
        }

        // Configuração para habilitar/desabilitar o serviço de overlay
        findPreference<SwitchPreferenceCompat>("enable_overlay")?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            if (enabled) {
                if (!Settings.canDrawOverlays(context)) {
                    // Se a permissão de overlay não estiver concedida, solicite-a
                    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).also {
                        startActivity(it)
                    }
                    false // Não altere a preferência ainda
                } else {
                    // Inicie o serviço de overlay
                    OverlayService.startService(requireContext())
                    true
                }
            } else {
                // Pare o serviço de overlay
                OverlayService.stopService(requireContext())
                true
            }
        }

        // Configuração para duração do overlay
        findPreference<Preference>("overlay_duration")?.setOnPreferenceChangeListener { _, newValue ->
            val duration = (newValue as String).toIntOrNull()
            if (duration != null && duration > 0) {
                OverlayService.setOverlayDuration(duration)
                true
            } else {
                // Mostrar um erro se o valor não for válido
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualizar o estado do switch com base na permissão real
        findPreference<SwitchPreferenceCompat>("enable_overlay")?.isChecked =
            Settings.canDrawOverlays(context)
    }
}
