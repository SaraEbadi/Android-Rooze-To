package com.saraebadi.github.roozeto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class CoffeeSoundReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentCoffeeService = new Intent(context,CoffeeSoundService.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            ContextCompat.startForegroundService(context,intentCoffeeService);
        }else {
            context.startService(intentCoffeeService);
        }
    }
}
