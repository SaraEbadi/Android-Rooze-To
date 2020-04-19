package com.saraebadi.github.roozeto.features.motivation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import com.saraebadi.github.roozeto.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotivationActivity extends AppCompatActivity {
    private VideoView videoView;
    private List<String> videoList = new ArrayList<>();
    private String videoUrl;
    private int videoCurrentPosition;
    private ProgressBar prgMotivation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);
        Toolbar toolbarMotivationActivity = findViewById(R.id.toolbarMotivation);
        prgMotivation = findViewById(R.id.prgMotivation);
        setSupportActionBar(toolbarMotivationActivity);
        getSupportActionBar().setTitle("ویدئو انگیزشی");
        if (savedInstanceState != null) {
            int current = savedInstanceState.getInt("videoCurrentPosition");
            videoUrl = savedInstanceState.getString("videoUrl");
            initializePlayer();
            videoView.seekTo(current);
            return;
        }
        checkNetworkInfo();
        initializePlayer();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoCurrentPosition = videoView.getCurrentPosition();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("videoCurrentPosition", videoCurrentPosition);
        outState.putString("videoUrl", videoUrl);
    }

    //check network is wifi or data on device
    private void checkNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            fillHighQuality();
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            fillLowQuality();
        }
    }

    private void initializePlayer() {
        MediaController mc = new MediaController(this);
        videoView = findViewById(R.id.videoView);
        videoView.setMediaController(mc);
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        stopProgressbar();
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }

    private void stopProgressbar() {
        videoView.setOnPreparedListener(e -> prgMotivation.setVisibility(View.GONE));
    }

    private void fillLowQuality() {
        videoList.add("https://hw13.cdn.asset.aparat.com/aparat-video/dc191ced3f723a7d151a8680df119b1d15747708-144p__19845.mp4");
        videoList.add("https://hw13.cdn.asset.aparat.com/aparat-video/71c6cc1b13fb9949ee681bfe191aec7215714896-144p__93256.mp4");
        videoList.add("https://hw15.cdn.asset.aparat.com/aparat-video/30cf882be8efa60754c916d4cd8d5cea15643664-144p__63386.mp4");
        videoList.add("https://hw4.cdn.asset.aparat.com/aparat-video/28ec42d497414783261ed34b2e0ee67815621595-144p__28505.mp4");
        videoList.add("https://hw4.cdn.asset.aparat.com/aparat-video/84264fbe304932bfeba403a6b47d4c9615669203-144p__87476.mp4");
        videoList.add("https://as4.cdn.asset.aparat.com/aparat-video/4086dc3926bb6c7a063cdaef674fcb9f15621579-144p__18907.mp4");
        videoList.add("https://hw19.cdn.asset.aparat.com/aparat-video/0593bd8cda5a6586fe4b90ed72437f8215668666-144p__33105.mp4");
        videoList.add("https://hw5.cdn.asset.aparat.com/aparat-video/90cd1501cb9c103ae1c062e747848f7715755201-144p__23907.mp4");
        videoList.add("https://hw14.cdn.asset.aparat.com/aparat-video/28dc409d71c8ec3ddb017a71201defd415719087-144p__72526.mp4");

        videoUrl = videoList.get(new Random().nextInt(videoList.size()));
    }

    private void fillHighQuality() {
        videoList.add("https://hw13.cdn.asset.aparat.com/aparat-video/dc191ced3f723a7d151a8680df119b1d15747708-720p__19845.mp4");
        videoList.add("https://hw13.cdn.asset.aparat.com/aparat-video/71c6cc1b13fb9949ee681bfe191aec7215714896-720p__93256.mp4");
        videoList.add("https://hw15.cdn.asset.aparat.com/aparat-video/30cf882be8efa60754c916d4cd8d5cea15643664-720p__63386.mp4");
        videoList.add("https://hw4.cdn.asset.aparat.com/aparat-video/28ec42d497414783261ed34b2e0ee67815621595-720p__28505.mp4");
        videoList.add("https://hw4.cdn.asset.aparat.com/aparat-video/84264fbe304932bfeba403a6b47d4c9615669203-720p__87476.mp4");
        videoList.add("https://as4.cdn.asset.aparat.com/aparat-video/4086dc3926bb6c7a063cdaef674fcb9f15621579-720p__18907.mp4");
        videoList.add("https://hw19.cdn.asset.aparat.com/aparat-video/0593bd8cda5a6586fe4b90ed72437f8215668666-720p__33105.mp4");
        videoList.add("https://hw5.cdn.asset.aparat.com/aparat-video/90cd1501cb9c103ae1c062e747848f7715755201-720p__23907.mp4");
        videoList.add("https://hw14.cdn.asset.aparat.com/aparat-video/28dc409d71c8ec3ddb017a71201defd415719087-720p__72526.mp4");
        videoUrl = videoList.get(new Random().nextInt(videoList.size()));
    }


}
