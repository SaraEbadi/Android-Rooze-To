package com.saraebadi.github.roozeto.features.downloader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saraebadi.github.roozeto.R;
import com.saraebadi.github.roozeto.model.Download;

import java.util.List;

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.downloadViewHolder> {

    Context context;
    List<Download> downloadList;

    public DownloadListAdapter(Context context, List<Download> downloadList) {
        this.context = context;
        this.downloadList = downloadList;
    }

    @NonNull
    @Override
    public downloadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.download_list_item,viewGroup,false);
        return new downloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull downloadViewHolder downloadViewHolder, int i) {
        downloadViewHolder.txtUrl.setText(downloadList.get(i).getDownloadUrl());
        int downloadIsComplete = downloadList.get(i).getDownloadComplete();
        if (downloadIsComplete == 0){
            downloadViewHolder.imgComplete.setImageResource(R.drawable.ic_download_error);
        }else{
            downloadViewHolder.imgComplete.setImageResource(R.drawable.ic_download_complete);
        }
    }

    @Override
    public int getItemCount() {
        return downloadList.size();
    }


    public class downloadViewHolder extends RecyclerView.ViewHolder {
        TextView txtUrl;
        ImageView imgComplete;
        public downloadViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUrl = itemView.findViewById(R.id.txtUrl);
            imgComplete = itemView.findViewById(R.id.img_complete);

        }
    }
}

