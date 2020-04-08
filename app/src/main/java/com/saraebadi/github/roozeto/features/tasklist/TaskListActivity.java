package com.saraebadi.github.roozeto.features.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.saraebadi.github.roozeto.AddTaskActivity;
import com.saraebadi.github.roozeto.features.edittask.EditTaskActivity;
import com.saraebadi.github.roozeto.OnClickItemListener;
import com.saraebadi.github.roozeto.R;
import com.saraebadi.github.roozeto.features.tasklist.adapter.TaskListAdapter;
import com.saraebadi.github.roozeto.database.DataSource;
import com.saraebadi.github.roozeto.model.Task;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    RecyclerView recyclerViewTaskList;
    Button btnAddTask;
    TaskListAdapter taskListAdapter;
    String date;
    Toolbar toolbar;
    DataSource dataSource;
    List<Task> taskList;
    CardView itemView;
    ImageView imgIsDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerViewTaskList=findViewById(R.id.recyclerviewTaskList);
        btnAddTask = findViewById(R.id.btnAddTask);
        itemView = findViewById(R.id.cardviewItem);
        imgIsDone = findViewById(R.id.imgIsDone);
        toolbar = findViewById(R.id.toolbar_task_list_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("لیست تسک ها");
        dataSource = new DataSource(this);




        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        getData(date);


        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TaskListActivity.this, AddTaskActivity.class);
                intent1.putExtra("date",date);
                startActivity(intent1);
            }
        });


        taskListAdapter.setOnClickView(new OnClickItemListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.imgIsDone :
                    Task task = taskList.get(position);
                    dataSource.open();
                    if (task.getTaskIsDone() == 1){
                        task.setTaskIsDone(0);
                    }else {
                        task.setTaskIsDone(1);
                        YoYo.with(Techniques.Pulse).duration(400).repeat(2).playOn(view);
                    }
                    dataSource.updateTaskDone(task);
                    dataSource.close();
                    taskListAdapter.notifyDataSetChanged();
                    break;

                case R.id.cardviewItem :
                    Intent intent = new Intent(TaskListActivity.this, EditTaskActivity.class);
                    intent.putExtra("taskId",taskList.get(position).getTaskID());
                    startActivity(intent);
                    break;

                }
            }

            @Override
            public void OnLongItemClick(View view, int position) {
                Toast.makeText(TaskListActivity.this, "hello hello ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getData(String date) {
        dataSource.open();
        taskList = dataSource.getSpecficDateTask(date);
        taskListAdapter = new TaskListAdapter(this, taskList,dataSource);
        dataSource.close();
        recyclerViewTaskList.setAdapter(taskListAdapter);
        recyclerViewTaskList.setLayoutManager(new LinearLayoutManager(this));
    }





    @Override
    protected void onResume() {
        super.onResume();
        getData(date);

    }
}
