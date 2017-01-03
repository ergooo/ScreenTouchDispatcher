package jp.ergo.android.screentouchdispatcher

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.*
import android.provider.Settings

class SettingsActivity : SettingsActivityBase() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)

        val switcher = findPreference("switcher")
        switcher.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            println("onPreferenceChange")
            if (preference.key == "switcher") {
                if (newValue is Boolean) {
                    return@OnPreferenceChangeListener onSwitchChanged(newValue)
                }
            }

            true
        }

        bindPreferenceSummaryToValue(findPreference("example_text"))
        bindPreferenceSummaryToValue(findPreference("example_list"))
    }

    private fun onSwitchChanged(newValue: Boolean): Boolean {
        if (newValue) {
            if (isMorLater && !canDrawOverlays(this@SettingsActivity)) {
                (findPreference("switcher") as SwitchPreference).isChecked = false
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + packageName))
                startActivity(intent)
                return false
            } else {
                val serviceIntent = Intent(this@SettingsActivity, ScreenTouchDispatcherService::class.java)
                serviceIntent.action = ScreenTouchDispatcherService.ACTION_START
                startService(serviceIntent)
            }

        } else {
            val serviceIntent = Intent(this@SettingsActivity, ScreenTouchDispatcherService::class.java)
            serviceIntent.action = ScreenTouchDispatcherService.ACTION_STOP
            startService(serviceIntent)
        }
        return true
    }

    private val isMorLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @TargetApi(23)
    private fun canDrawOverlays(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    private val isServiceOn: Boolean
        get() {
            val preference = findPreference("switcher") as SwitchPreference
            return preference.isChecked
        }

    private fun setServiceOn() {
        val preference = findPreference("switcher") as SwitchPreference
        preference.isChecked = true
    }

}
