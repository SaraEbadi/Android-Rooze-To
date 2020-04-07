package com.saraebadi.github.roozeto;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.saraebadi.github.roozeto.adapter.DownloadListAdapter;
import com.saraebadi.github.roozeto.database.DataSource;
import com.saraebadi.github.roozeto.model.Download;

import java.io.File;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    EditText edtUrl;
    Button btnDownload;
    ProgressBar progressBar;
    String url;
    ImageView imageView;
    Toolbar toolbar;
    String fileName;
    DataSource dataSource;
    RecyclerView recyclerViewDownloadList;
    InputMethodManager imm;
    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        dataSource = new DataSource(DownloadActivity.this);
        dataSource.open();

        recyclerViewDownloadList = findViewById(R.id.recycler_view_download_list);
        toolbar = findViewById(R.id.toolbar_download_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("دانلود ها");

        edtUrl = findViewById(R.id.edt_url);
        btnDownload = findViewById(R.id.btn_download);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.image_view);
        checkForPermission();


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = edtUrl.getText().toString();
                List<Download> downloadList = dataSource.getUrlDownloadList();
                if (!url.isEmpty()){
                    for(Download item : downloadList){
                        if (item.getDownloadUrl().equals(url)){

                            //Hide keyboard after button click when download link exist

                            Toast.makeText(DownloadActivity.this, "این لینک قبلا وارد شده", Toast.LENGTH_LONG).show();
                            edtUrl.setText("");
                            return;
                        }
                    }


                    imageView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    downloadLink();


                    //Hide keyboard after button click when download link saved
                    try {
                        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                }else {
                    Toast.makeText(DownloadActivity.this, "لطفا لینک مورد نظر را وارد نمایید", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void downloadLink(){

        fileName = url.substring(url.lastIndexOf("/")+1);
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download";
        PRDownloader.download(url,dirPath,fileName).build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Log.i("Download", "onStartOrResume: ");
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Log.i("Download", "onDownloadComplete: ");
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setImageResource(R.drawable.ic_download_complete);
                        imageView.setVisibility(View.VISIBLE);
                        Download downloadCompelete = new Download(url,1);
                        dataSource.addDownloadComplete(downloadCompelete);
                        getDownloadItem();
                        edtUrl.setText("");


                    }


                    @Override
                    public void onError(Error error) {
                        Log.i("Download", "onError: "+error.getConnectionException());
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setImageResource(R.drawable.ic_download_error);
                        imageView.setVisibility(View.VISIBLE);
                        Download downloadError = new Download(url,0);
                        dataSource.addDownloadError(downloadError);
                        getDownloadItem();
                        edtUrl.setText("");
                    }
                });
    }

    //get all download list from database
    public void getDownloadItem(){
        List<Download> downloadList = dataSource.getDownloadLists();
        DownloadListAdapter downloadListAdapter = new DownloadListAdapter(DownloadActivity.this,downloadList);
        recyclerViewDownloadList.setAdapter(downloadListAdapter);
        recyclerViewDownloadList.setLayoutManager(new LinearLayoutManager(this));
        downloadListAdapter.notifyDataSetChanged();
    }


    public void checkForPermission(){
        if(Build.VERSION.SDK_INT>=23){
            String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if(!hasPermission(DownloadActivity.this,permissions)) {
                ActivityCompat.requestPermissions(DownloadActivity.this, permissions, REQUEST);
            }else{
                btnDownload.setEnabled(true);
            }
        }else {
            btnDownload.setEnabled(true);
        }
    }


    public boolean hasPermission(Context context, String...permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context != null && permissions !=null){
            for (String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btnDownload.setEnabled(true);
                } else {
                    btnDownload.setEnabled(false);
                    Toast.makeText(DownloadActivity.this, "شما به اپلیکیشن دسترسی لازم را نداده اید.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDownloadItem();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
