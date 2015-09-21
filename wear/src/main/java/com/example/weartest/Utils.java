package com.example.weartest;

import android.content.Context;
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
}
