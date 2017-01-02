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


/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 *
 * See [
   * Android Design: Settings](http://developer.android.com/design/patterns/settings.html) for design guidelines and the [Settings
   * API Guide](http://developer.android.com/guide/topics/ui/settings.html) for more information on developing a Settings UI.
 */
class SettingsActivity : PreferenceActivity() {
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
                startService(serviceIntent)
                NotificationUtils.sendNotification(this@SettingsActivity, "タッチして画面をロック")
            }

        } else {
            NotificationUtils.removeNotification(this@SettingsActivity)
        }
        return true
    }

    private val isMorLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @TargetApi(23)
    private fun canDrawOverlays(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }


    /**
     * {@inheritDoc}
     */
    override fun onIsMultiPane(): Boolean {
        return isXLargeTablet(this)
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

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("example_text"))
            bindPreferenceSummaryToValue(findPreference("example_list"))
        }
    }

    override fun isValidFragment(fragmentName: String): Boolean {
        return GeneralPreferenceFragment::class.java.name == fragmentName
    }

    companion object {

        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         */
        private fun isXLargeTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()
            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val index = preference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                        if (index >= 0)
                            preference.entries[index]
                        else
                            null)
            } else {

                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.

         * @see .sBindPreferenceSummaryToValueListener
         */
        private fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.context)
                            .getString(preference.key, ""))
        }
    }
}
