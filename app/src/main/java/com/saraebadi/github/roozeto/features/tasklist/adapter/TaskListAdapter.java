package com.saraebadi.github.roozeto.features.tasklist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saraebadi.github.roozeto.OnClickItemListener;
import com.saraebadi.github.roozeto.R;
import com.saraebadi.github.roozeto.database.DataSource;
import com.saraebadi.github.roozeto.model.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private static OnClickItemListener onClickItemListener;
    private Context context;
    private List<Task> taskList;
    DataSource dataSource;

    public TaskListAdapter(Context context, List<Task> taskList, DataSource dataSource) {
        this.context = context;
        this.taskList = taskList;
        this.dataSource = dataSource;

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_list_item, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.txtTitle.setText(taskList.get(i).getTaskTitle());
        int isDone = taskList.get(i).getTaskIsDone();
        if (isDone == 1) {
            taskViewHolder.imgIsDone.setImageResource(R.drawable.ic_done);
        } else {
            taskViewHolder.imgIsDone.setImageResource(R.drawable.ic_not_done);

        }
//        taskViewHolder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

//    public void setOnClickListener(View.OnClickListener onClickListener){
//        this.onClickListener = onClickListener;
//
//    }


    public void setOnClickView(final OnClickItemListener onClickItemListener) {
        TaskListAdapter.onClickItemListener = onClickItemListener;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtTitle;
        ImageView imgIsDone;
        CardView item;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgIsDone = itemView.findViewById(R.id.imgIsDone);
            item = itemView.findViewById(R.id.cvItem);

            imgIsDone.setOnClickListener(this);
            item.setOnClickListener(this);
            item.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onClickItemListener.OnItemClick(v,getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onClickItemListener.OnLongItemClick(v, getAdapterPosition());
            return false;
        }


//        public void setOnClickListener() {
//            imgIsDone.setOnClickListener(TaskViewHolder.this);
//            item.setOnClickListener(TaskViewHolder.this);
//        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.imgIsDone:
//                    Task task = taskList.get(getAdapterPosition());
//                    dataSource.open();
//                    if (task.getTaskIsDone() == 1) {
//                        task.setTaskIsDone(0);
//                    } else {
//                        task.setTaskIsDone(1);
//                        YoYo.with(Techniques.Shake).duration(300).repeat(1).playOn(itemView);
//                    }
//                    dataSource.updateTaskDone(task);
//                    dataSource.close();
//                    notifyDataSetChanged();
//                    break;
//                case R.id.cardviewItem :
//                    Intent intent = new Intent(context, EditTaskActivity.class);
//                    intent.putExtra("taskId",taskList.get(getAdapterPosition()).getTaskID());
//                    context.startActivity(intent);
//
//            }
        }
    }







