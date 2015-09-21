package com.example.weartest;

import android.content.Context;
import android.os.PowerManager;

public class Utils {

    public static void turnScreenOn(Context context) {
        PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(context.POWER_SERVICE))
                .newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "");
        wakeLock.acquire();
        wakeLock.release();
    }
}
