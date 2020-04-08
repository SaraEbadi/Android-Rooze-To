package com.saraebadi.github.roozeto;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.saraebadi.github.roozeto.adapter.DateListAdapter;
import com.saraebadi.github.roozeto.database.DataSource;
import com.saraebadi.github.roozeto.features.downloader.DownloadActivity;
import com.saraebadi.github.roozeto.features.morningalarm.MorningActivity;
import com.saraebadi.github.roozeto.features.motivation.MotivationActivity;
import com.saraebadi.github.roozeto.lib.Roozh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    DataSource dataSource;
    Toolbar toolbarMainActivity;
    List<String> dateList =new ArrayList<>();
    RecyclerView dateRecyclerView;
    RoundedImageView imgMotivation;
    RoundedImageView imgCoffee;
    RoundedImageView imgTea;
    RoundedImageView imgAlarm;
    RoundedImageView imgDownload;
    RoundedImageView imgMedicine;
    Calendar calendar;
    AlarmManager alarmManager;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarMainActivity = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbarMainActivity);
        imgMotivation = findViewById(R.id.img_motivation);
        imgCoffee = findViewById(R.id.img_coffee);
        imgTea = findViewById(R.id.img_tea);
        imgDownload = findViewById(R.id.img_download);
        imgAlarm = findViewById(R.id.img_sleep);
        imgMedicine = findViewById(R.id.img_medicine);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        progressBarHolder = findViewById(R.id.progressBarHolder);
            imgCoffee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isServiceRunning(CoffeeSoundService.class)){
                        Toast.makeText(MainActivity.this, "آلارم قهوه در حال حاضر فعال است", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "منتظر بمانید ...", Toast.LENGTH_SHORT).show();
                    }else {
                        calendar = Calendar.getInstance();
                        YoYo.with(Techniques.Pulse)
                                .duration(500)
                                .repeat(2)
                                .playOn(imgCoffee);
                        int minute = calendar.get(calendar.MINUTE);
                        int hour = calendar.get(calendar.HOUR_OF_DAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        final Intent alarmReceiverIntent = new Intent(
                                MainActivity.this, CoffeeSoundReceiver.class);
                        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
                                MainActivity.this, 0, alarmReceiverIntent, PendingIntent.FLAG_ONE_SHOT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent1);
                        new TaskCoffee().execute();
                        Toast.makeText(MainActivity.this, "آلارم قهوه تون فعال شد", Toast.LENGTH_LONG).show();
                    }
                    }



            });
        imgTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceRunning(TeaSoundService.class)){
                    Toast.makeText(MainActivity.this, "آلارم چایی در حال حاضر فعال است", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "منتظر بمانید ...", Toast.LENGTH_SHORT).show();
                }else {
                    YoYo.with(Techniques.Pulse)
                            .duration(500)
                            .repeat(2)
                            .playOn(imgTea);
                    calendar = Calendar.getInstance();
                    int minute = calendar.get(calendar.MINUTE);
                    int hour = calendar.get(calendar.HOUR_OF_DAY);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    final Intent alarmReceiverIntent = new Intent(MainActivity.this, TeaSoundReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmReceiverIntent, PendingIntent.FLAG_ONE_SHOT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    new TaskTea().execute();
                    Toast.makeText(MainActivity.this, "آلارم چایی فعال شد", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMorninActivity = new Intent(MainActivity.this, MorningActivity.class);
                startActivity(intentMorninActivity);
            }
        });

        imgMotivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMotivationActivity = new Intent(MainActivity.this, MotivationActivity.class);
                startActivity(intentMotivationActivity);
            }
        });

        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDownloadManager = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intentDownloadManager);
            }
        });

        imgMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "این آیتم در دست تعمیر است", Toast.LENGTH_SHORT).show();
            }
        });

        dataSource = new DataSource(this);
        dataSource.open();
        toolbarMainActivity = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbarMainActivity);
        dateRecyclerView = findViewById(R.id.recyclerViewDateList);
        DateListAdapter dateListAdapter = new DateListAdapter(MainActivity.this,getDaysList(4));
        dateRecyclerView.setAdapter(dateListAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //create and inflate menu on activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);
        return true;
    }

    //onclick item menu action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.first_option:
                Intent intentDownloadActivity = new Intent(MainActivity.this,DownloadActivity.class);
                startActivity(intentDownloadActivity);
                break;
            case R.id.second_option:
                Intent intentMotivationActivity = new Intent(MainActivity.this,MotivationActivity.class);
                startActivity(intentMotivationActivity);
                break;
            case R.id.third_option:
                Toast.makeText(this, "این آیتم در دست تعمیر است", Toast.LENGTH_SHORT).show();
//                Intent intentMedicineActivity = new Intent(MainActivity.this,MedicineActivity.class);
//                startActivity(intentMedicineActivity);
                break;
            case R.id.four_option:
                Intent intentMorningActivity = new Intent(MainActivity.this,MorningActivity.class);
                startActivity(intentMorningActivity);
                break;
                default:
                    Toast.makeText(this, "گزینه ای موجود نیست", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    //get year,month,numberOfDays Gregorian calendar and convert to Solar calendar with library Roozh
    public List<String>  getDaysList(int numberOfDays){

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i<numberOfDays ; i++) {
            Roozh roozh = new Roozh();
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH)+1;
            int year = calendar.get(Calendar.YEAR);
            roozh.GregorianToPersian(year,month,dayOfMonth);
            String roozhToday = getDayPersianString(calendar) + " " +roozh.toString();
            dateList.add(roozhToday);
            calendar.add(Calendar.DATE,1);
        }
        return dateList;
    }

    public String getDateMarked(String fromDate, String toDate){//21/12/2121
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String startDate = simpleDate.format(simpleDate.parse(fromDate));
            Log.i("tag", "getDateMarked: "+startDate);
            Date endDate = simpleDate.parse(toDate);
            String str[] = startDate.split("/");
            String day = str[0];
            String month = str[1];
            String year = str[2];
            Log.i("tag", "getDateMarked: "+day);
            Log.i("tag", "getDateMarked: "+month);
            Log.i("tag", "getDateMarked: "+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //.................get current date week name
    public String getDayPersianString (Calendar calendar){
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.SATURDAY:
                return "شنبه";
            case Calendar.SUNDAY:
                return "یکشنبه";
            case Calendar.MONDAY:
                return "دوشنبه";
            case Calendar.TUESDAY:
                return "سه شنبه";
            case Calendar.WEDNESDAY:
                return "چهارشنبه";
            case Calendar.THURSDAY:
                return "پنج شنبه";
            case Calendar.FRIDAY:
                return "جمعه";
        }
        return null;
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("service", "isServiceRunning: "+ serviceClass.getName().equals(service.service.getClassName()));
                    return true;
            }
        }
        return false;
    }

    private class TaskCoffee extends AsyncTask <Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgCoffee.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            imgCoffee.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    Log.d("progressbar", "Emulating some task.. Step " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class TaskTea extends AsyncTask <Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgTea.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            imgTea.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    Log.d("progressbar", "Emulating some task.. Step " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
