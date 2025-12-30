package org.lineageos.settings.glove;

import android.os.Bundle;
import androidx.preference.PreferenceFragment;

public class GloveModeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GloveModeFragment())
                .commit();
    }
}
