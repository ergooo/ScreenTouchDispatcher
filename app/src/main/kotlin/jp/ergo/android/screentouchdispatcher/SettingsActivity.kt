package jp.ergo.android.screentouchdispatcher

import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.SwitchPreference
import android.provider.Settings


class SettingsActivity : SettingsActivityBase() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)

        val switcher = findPreference("switcher") as SwitchPreference
        switcher.isChecked = isServiceRunning(ScreenTouchDispatcherService.javaClass)
        switcher.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            if (preference.key == "switcher") {
                if (newValue is Boolean) {
                    return@OnPreferenceChangeListener onSwitchChanged(newValue)
                }
            }

            true
        }
    }

    private fun onSwitchChanged(newValue: Boolean): Boolean {
        if (newValue) {
            if (isMorLater && !canDrawOverlays(this@SettingsActivity)) {
                // MならPermissionのチェック
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

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name.startsWith(it.service.className) }
    }

}
