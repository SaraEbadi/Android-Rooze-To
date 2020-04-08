package com.saraebadi.github.roozeto.features.morningalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.saraebadi.github.roozeto.features.morningalarm.MorningService;

public class MorningAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentReciver = new Intent(context, MorningService.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intentReciver);
        }else {
            context.startService(intentReciver);
        }
    }

}
