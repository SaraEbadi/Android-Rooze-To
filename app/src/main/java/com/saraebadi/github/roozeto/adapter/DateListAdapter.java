package com.saraebadi.github.roozeto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saraebadi.github.roozeto.R;
import com.saraebadi.github.roozeto.features.tasklist.TaskListActivity;

import java.util.List;

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.DateViewHolder> {


    private Context context;
    private List<String> dateList;
    public DateListAdapter(Context context, List<String> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.date_list_item,viewGroup,false);
        return new DateViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DateViewHolder dateViewHolder, final int i) {
        dateViewHolder.textViewDate.setText(dateList.get(i));
        dateViewHolder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }



    public class DateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewDate;
        CardView cardViewDate;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate= itemView.findViewById(R.id.txtDate);
            cardViewDate = itemView.findViewById(R.id.cvDate);
        }

        public void setOnClickListener (){
            cardViewDate.setOnClickListener(DateViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cvDate:
                Intent intentAddTask = new Intent(context, TaskListActivity.class);
                intentAddTask.putExtra("date",dateList.get(getAdapterPosition()));
                context.startActivity(intentAddTask);
                break;
            }
        }
    }


}
