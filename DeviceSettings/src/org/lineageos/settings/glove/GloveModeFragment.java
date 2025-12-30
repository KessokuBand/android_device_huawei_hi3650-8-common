package org.lineageos.settings.glove;

import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import java.io.File;

public class GloveModeFragment extends PreferenceFragmentCompat {
    private static final String TAG = "GloveModeFragment";
    private static final String GLOVE_PATH = "/sys/touchscreen/touch_glove";
    private static final String KEY_GLOVE_SWITCH = "glove_mode_switch";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.glove_mode_prefs);
        SwitchPreferenceCompat gloveSwitch = findPreference(KEY_GLOVE_SWITCH);
        if (gloveSwitch == null) return;

        updateSwitchState(gloveSwitch);

        gloveSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean enabled = (Boolean) newValue;
            if (setGloveModeEnabled(enabled)) {
                return true;
            } else {
                updateSwitchState(gloveSwitch);
                return false;
            }
        });
    }

    private void updateSwitchState(SwitchPreferenceCompat pref) {
        pref.setChecked(isGloveModeEnabled());
    }

    private boolean isGloveModeEnabled() {
        File file = new File(GLOVE_PATH);
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        try {
            String value = FileUtils.readTextFile(file, 0, null).trim();
            return "1".equals(value);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read glove mode state", e);
            return false;
        }
    }

    private boolean setGloveModeEnabled(boolean enabled) {
        try {
            FileUtils.stringToFile(GLOVE_PATH, enabled ? "1" : "0");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to set glove mode", e);
            return false;
        }
    }
}
