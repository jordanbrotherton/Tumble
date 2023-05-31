package com.jordanjbrotherton.tumble;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.google.android.material.color.DynamicColors;

public class TumbleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Monet (Android 12+)
        DynamicColors.applyToActivitiesIfAvailable(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
