package com.threetwentyfivedesigns.tickerio.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.threetwentyfivedesigns.tickerio.R;
import com.threetwentyfivedesigns.tickerio.theme.ThemeHelper;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);

            final ListPreference themePreference = findPreference("themePref");
            //Theme Select
            if (themePreference != null) {
                themePreference.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            String themeOption = (String) newValue;
                            ThemeHelper.applyTheme(themeOption);
                            return true;
                        }
                    }
                 );
            }
        }
    }
}