package com.saraebadi.github.roozeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AlarmActivity extends AppCompatActivity {

    Button btnPressedAlarm;
    TextView txtQues;
    TextView txtCurrentTimeAlarm;
    EditText edtAns;
    int selection;
    String minute;
    int hour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram);
        final String[] ques = {" = ۲ × ۵ + ۶ ", " = ۴ + ۸ × ۳ ", " = ۹ ÷ ۳ + ۳ ", " = ۲۱ × ۲ - ۵ ", " = ۱۴ × ۱ + ۱۰ ", " = ۳ + ۸ ÷ ۲ ", " = ۲۱ × ۵ "};
        final String[] ans = {"16", "28", "6", "37", "24", "7", "105"};
        btnPressedAlarm = findViewById(R.id.btn_pressed_alarm);
        txtQues = findViewById(R.id.txt_ques);
        txtCurrentTimeAlarm = findViewById(R.id.txt_current_time_alarm);
        edtAns = findViewById(R.id.edt_ans);
        selection = new Random().nextInt(ques.length);
        txtQues.setText(ques[selection]);
        btnPressedAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAns.getText().toString().equals(ans[selection])) {

                    Intent intentStopService = new Intent(AlarmActivity.this, MorningService.class);
                    stopService(intentStopService);
                    finish();
                } else {
                    Toast.makeText(AlarmActivity.this, " جواب شما نادرست است دوباره تلاش کنید.  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
//    }
}