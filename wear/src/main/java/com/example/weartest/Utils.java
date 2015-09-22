package com.example.weartest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.Vibrator;

public class Utils {

    public static void turnScreenOn(Context context) {
        PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "");
        wakeLock.acquire();
        wakeLock.release();
    }

    public static void vibrate(Context context, long[] mVibrationPattern, int mIndexInPatternToRepeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(mVibrationPattern, mIndexInPatternToRepeat);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.shared_preferences_file_key), Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }
}
