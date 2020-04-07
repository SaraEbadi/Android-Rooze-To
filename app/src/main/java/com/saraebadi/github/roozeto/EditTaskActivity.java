package com.saraebadi.github.roozeto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.saraebadi.github.roozeto.database.DataSource;
import com.saraebadi.github.roozeto.model.Task;

public class EditTaskActivity extends AppCompatActivity {
    EditText edtEditStringTask;
    EditText edtEditTitleTask;
    Button btnEditTask;
    Button btnDeleteTask;
    DataSource dataSource;
    Task task;
    Toolbar toolbarEditTaskActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        edtEditStringTask = findViewById(R.id.edtStringTask);
        edtEditTitleTask = findViewById(R.id.EdtTitleTask);
        btnEditTask = findViewById(R.id.edit_task_btn);
        btnDeleteTask = findViewById(R.id.delete_task_btn);
        toolbarEditTaskActivity = findViewById(R.id.toolbar_edi_task_activity);
        setSupportActionBar(toolbarEditTaskActivity);
        getSupportActionBar().setTitle("ویرایش تسک");

        edtEditTitleTask.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtEditTitleTask, InputMethodManager.SHOW_IMPLICIT);


        final Intent intent = getIntent();
        int taskId = intent.getIntExtra("taskId",1);

        dataSource = new DataSource(this);
        dataSource.open();
         task = dataSource.getTaskSpecificId(taskId);


        edtEditTitleTask.setText(task.getTaskTitle());
        edtEditStringTask.setText(task.getTaskString());

        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTaskTitle(edtEditTitleTask.getText().toString());
                task.setTaskString(edtEditStringTask.getText().toString());
                dataSource.updateTask(task);
                Intent intent1 =new Intent(EditTaskActivity.this,TaskListActivity.class);
                intent1.putExtra("date",task.getTaskDate());
                startActivity(intent1);
                finish();


            }
        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                builder.setTitle(" حذف تسک ");
                builder.setMessage(" آیا برای حذف تسک مطمئن هستید ؟ ");
                builder.setPositiveButton(" بله ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dataSource.deleteTask(task);
                        Intent deleteIntent = new Intent(EditTaskActivity.this,TaskListActivity.class);
                        deleteIntent.putExtra("date",task.getTaskDate());
                        startActivity(deleteIntent);
                        finish();
                    }
                });
                builder.setNegativeButton(" خیر ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
