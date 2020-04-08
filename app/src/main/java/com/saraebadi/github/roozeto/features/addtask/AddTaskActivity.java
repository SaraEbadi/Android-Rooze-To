package com.saraebadi.github.roozeto.features.addtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.saraebadi.github.roozeto.R;
import com.saraebadi.github.roozeto.database.DataSource;
import com.saraebadi.github.roozeto.features.tasklist.TaskListActivity;
import com.saraebadi.github.roozeto.model.Task;

public class AddTaskActivity extends AppCompatActivity {
    //global field object
    EditText titleTaskEdt;
    EditText stringTaskEdt;
    Button addTaskBtn;
    DataSource taskDataSource;
    Toolbar toolbarAddTaskActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        toolbarAddTaskActivity = findViewById(R.id.toolbar_add_task_activity);
        setSupportActionBar(toolbarAddTaskActivity);
        getSupportActionBar().setTitle(" تسک جدید ");


        //initialize database and open for change database
        taskDataSource = new DataSource(AddTaskActivity.this);
        taskDataSource.open();

        //get date specific from MainActivity date for show taskList date specific
        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");

        //convert view android to java object
        titleTaskEdt = findViewById(R.id.edtEditTitleTask);
        stringTaskEdt = findViewById(R.id.edtEditStringTask);
        addTaskBtn = findViewById(R.id.delete_task_btn);


        //click button addTask and Add new task for date specific
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleTask = titleTaskEdt.getText().toString();
                String stringTask = stringTaskEdt.getText().toString();
                if (titleTask.isEmpty()){
                    Toast.makeText(AddTaskActivity.this, "لطفا موضوع تسک را وارد کنید", Toast.LENGTH_LONG).show();
                }else if (stringTask.isEmpty()){
                    Toast.makeText(AddTaskActivity.this, "توضیحاتی برای تسک وارد نمایید", Toast.LENGTH_LONG).show();
                }else {
                    Task task = new Task(titleTask,stringTask,0,date);
                    taskDataSource.addTask(task);
                    Intent intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
                    intent.putExtra("date",date);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    //close databaseSqlite for RAM
    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskDataSource.close();

    }
}
