package com.nilhcem.shammane.prefs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nilhcem.shammane.R;

public class PreferencesActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private PreferencesHelper prefs;
    private CheckBox showIndicatorsCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new PreferencesHelper(this);
        setContentView(R.layout.preferences);
        showIndicatorsCheckbox = (CheckBox) findViewById(R.id.show_indicators);
        showIndicatorsCheckbox.setChecked(prefs.showIndicators());
        showIndicatorsCheckbox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        prefs.setShowIndicators(isChecked);
    }
}
