package com.saraebadi.github.roozeto;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static com.saraebadi.github.roozeto.Configuration.CHANNEL_ID;

public class TeaSoundService extends IntentService {

    MediaPlayer mediaPlayerTea1;
    MediaPlayer mediaPlayerTea2;
    private PowerManager.WakeLock wakeLock;

    public TeaSoundService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
        setIntentRedelivery(true);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"ExampleApp:WakeLock");
        wakeLock.acquire();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent intentAlarm = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intentAlarm,0);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("آلارم چایی")
                .setContentText("تا ۵ دقیقه دیگه بهتون یادآوری میشه چایی تون آمادس !")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);


        SystemClock.sleep(900000);
        alarmVibrator();
        mediaPlayerTea1 = MediaPlayer.create(getApplicationContext(),R.raw.tea_is_ready2);
        mediaPlayerTea1.start();

        SystemClock.sleep(720000);
        alarmVibrator();
        mediaPlayerTea2 = MediaPlayer.create(getApplicationContext(),R.raw.tea_is_ready3);
        mediaPlayerTea2.start();

        SystemClock.sleep(7000);
        this.stopSelf();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock.release();

    }

    public void alarmVibrator(){
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
