package com.saraebadi.github.roozeto;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import static com.saraebadi.github.roozeto.Configuration.CHANNEL_ID;

public class MorningService extends Service {
    MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.mornning_alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        Intent intentAlarm = new Intent(this, AlarmActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intentAlarm,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("هشدار")
                .setContentText("لطفا از خواب بیدار شوید.")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
