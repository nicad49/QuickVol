package com.devshed42.quickvol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class VolumeChangeReceiver extends BroadcastReceiver {

    private final String LOG_TAG = VolumeChangeReceiver.class.getSimpleName();

    public VolumeChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        QuickVolWidget.updateWidgets(context);

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // set widget to proper icon after boot
            Log.d(LOG_TAG, "Post boot check");
        } else {
            Log.d(LOG_TAG, "Ringer mode changed");
        }


    }
}
