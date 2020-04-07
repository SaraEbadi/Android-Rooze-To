package com.saraebadi.github.roozeto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class TeaSoundReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentTeaService = new Intent(context,TeaSoundService.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            ContextCompat.startForegroundService(context,intentTeaService);
        }else {
            context.startService(intentTeaService);
        }
    }
}
