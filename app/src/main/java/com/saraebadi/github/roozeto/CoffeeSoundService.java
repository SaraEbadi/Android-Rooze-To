package com.saraebadi.github.roozeto;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import static com.saraebadi.github.roozeto.Configuration.CHANNEL_ID;


public class CoffeeSoundService extends IntentService {
    MediaPlayer mediaPlayerCoffee;
    MediaPlayer mediaPlayerCoffee2;
    private PowerManager.WakeLock wakeLock;

    public CoffeeSoundService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ExampleApp:WakeLock");
        wakeLock.acquire();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service", "onHandleWork: jobIntentService");

        Intent intentAlarm = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentAlarm, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("آلارم قهوه")
                .setContentText("تا  دقیقه دیگه بهتون یادآوری میشه قهوه تون فراموش نشه !")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        SystemClock.sleep(480000);
        alarmVibrator();
        mediaPlayerCoffee = MediaPlayer.create(getApplicationContext(), R.raw.coffee_is_ready1);
        mediaPlayerCoffee.start();
        Log.i("service", "onHandleWork: alarm1");
        SystemClock.sleep(840000);
        alarmVibrator();
        mediaPlayerCoffee2 = MediaPlayer.create(getApplicationContext(), R.raw.coffee_is_ready2);
        mediaPlayerCoffee2.start();
        SystemClock.sleep(7000);
        this.stopSelf();
        Log.i("service", "onHandleWork: stop service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayerCoffee.stop();
        wakeLock.release();
    }

    public void alarmVibrator() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 1000 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.PARCELABLE_WRITE_RETURN_VALUE));
        } else {
            //deprecated in API 26
            v.vibrate(1000);
        }
    }
}
