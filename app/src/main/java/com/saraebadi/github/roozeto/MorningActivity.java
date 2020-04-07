package com.saraebadi.github.roozeto;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MorningActivity extends AppCompatActivity {

    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    Button btnStartAlarm;
    int minute;
    int hour;
    int currentApiVersion = Build.VERSION.SDK_INT;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning);
        timePicker = findViewById(R.id.time_picker);
        btnStartAlarm = findViewById(R.id.btn_start_alarm);
        calendar = Calendar.getInstance();


        //when click on btnStartAlarm alarm set on specify hour and minutes and start
        btnStartAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1){
                    minute = timePicker.getMinute();
                    hour = timePicker.getHour();
                }else {
                    minute = timePicker.getCurrentMinute();
                    hour = timePicker.getCurrentHour();
                }
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



                Intent intentAlarmBroadCastReciver = new Intent(MorningActivity.this,MorningAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MorningActivity.this,0,intentAlarmBroadCastReciver,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                Intent intent = new Intent(MorningActivity.this,MainActivity.class);
                startActivity(intent);



            }
        });



    }


}
