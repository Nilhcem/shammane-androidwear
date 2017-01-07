package com.nilhcem.shammane;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.Gravity;
import android.view.SurfaceHolder;

import com.nilhcem.shammane.core.WatchMode;
import com.nilhcem.shammane.core.WatchShape;
import com.nilhcem.shammane.prefs.PreferencesHelper;

public class MyWatchFace extends BaseWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends BaseWatchFaceService.Engine implements SharedPreferences.OnSharedPreferenceChangeListener {

        private PreferencesHelper prefs;
        private MyWatchFaceRenderer watch;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            prefs = new PreferencesHelper(context);
            prefs.registerOnSharedPreferenceChangeListener(this);
            watch = new MyWatchFaceRenderer(context);
            watch.setShowIndicators(prefs.showIndicators());

            setWatchFaceStyle(new WatchFaceStyle.Builder(MyWatchFace.this)
                    .setAcceptsTapEvents(false)
                    .setAmbientPeekMode(WatchFaceStyle.AMBIENT_PEEK_MODE_VISIBLE)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setHotwordIndicatorGravity(Gravity.TOP)
                    .setPeekOpacityMode(WatchFaceStyle.PEEK_OPACITY_MODE_OPAQUE)
                    .setShowSystemUiTime(false)
                    .setShowUnreadCountIndicator(true)
                    .setStatusBarGravity(Gravity.TOP)
                    .setViewProtectionMode(WatchFaceStyle.PROTECT_STATUS_BAR | WatchFaceStyle.PROTECT_HOTWORD_INDICATOR)
                    .build());
        }

        @Override
        protected void onWatchModeChanged(WatchMode mode) {
            watch.setMode(mode);
        }

        @Override
        protected void onScreenSizeChanged(int newWidth, int newHeight, int chinSize, WatchShape shape) {
            watch.setSize(newWidth, newHeight, chinSize, shape);
            invalidate();
        }

        @Override
        protected void onDrawTime(Canvas canvas, float angleHours, float angleMinutes, float angleSeconds) {
            watch.drawTime(canvas, angleHours, angleMinutes, angleSeconds);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            watch.setShowIndicators(prefs.showIndicators());
            invalidate();
        }

        @Override
        public void onDestroy() {
            prefs.unregisterOnSharedPreferenceChangeListener(this);
            super.onDestroy();
        }
    }
}
