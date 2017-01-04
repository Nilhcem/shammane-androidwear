package com.nilhcem.shammane.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    private static final String PREFS_NAME = "config";
    private static final String KEY_SHOW_INDICATORS = "show_indicators";

    private final SharedPreferences prefs;

    public PreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean showIndicators() {
        return prefs.getBoolean(KEY_SHOW_INDICATORS, false);
    }

    public void setShowIndicators(boolean showIndicators) {
        prefs.edit().putBoolean(KEY_SHOW_INDICATORS, showIndicators).apply();
    }
}
