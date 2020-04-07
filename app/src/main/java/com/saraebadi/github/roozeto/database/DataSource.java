package com.saraebadi.github.roozeto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saraebadi.github.roozeto.model.Download;
import com.saraebadi.github.roozeto.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseSQliteOpenHelper databaseSQliteOpenHelper;

    public DataSource(Context context) {
        databaseSQliteOpenHelper = new DatabaseSQliteOpenHelper(context);
    }

    public void open() {
        this.sqLiteDatabase = databaseSQliteOpenHelper.getWritableDatabase();
    }

    public void close() {
        databaseSQliteOpenHelper.close();
    }

    public boolean addTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TaskTableFields.COLUMN_TITLE, task.getTaskTitle());
        contentValues.put(Contract.TaskTableFields.COLUMN_STRING, task.getTaskString());
        contentValues.put(Contract.TaskTableFields.COLUMN_IS_DONE, task.getTaskIsDone());
        contentValues.put(Contract.TaskTableFields.COLUMN_DATE, task.getTaskDate());
        double resault_task = sqLiteDatabase.insert(Contract.TaskTableFields.TABLE_NAME, null, contentValues);
        if (resault_task == -1) {
            return false;
        }
        return true;
    }

    public boolean addDownloadComplete(Download downloadComplete){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.DownloadTableFields.COLUMN_URL,downloadComplete.getDownloadUrl());
        contentValues.put(Contract.DownloadTableFields.COLUMN_COMPLETE,downloadComplete.getDownloadComplete());
        double resultDownload = sqLiteDatabase.insert(Contract.DownloadTableFields.TABLE_NAME,null,contentValues);
        if (resultDownload == -1){
            return false;
        }
        return true;
    }

    public boolean addDownloadError(Download downloadError){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.DownloadTableFields.COLUMN_URL,downloadError.getDownloadUrl());
        contentValues.put(Contract.DownloadTableFields.COLUMN_COMPLETE,downloadError.getDownloadComplete());
        double resultDownload = sqLiteDatabase.insert(Contract.DownloadTableFields.TABLE_NAME,null,contentValues);
        if (resultDownload == -1){
            return false;
        }
        return true;
    }





    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        String query = " select * from " + Contract.TaskTableFields.TABLE_NAME + " order by " + Contract.TaskTableFields.COLUMN_ID + " asc ";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_TITLE));
                String String = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_STRING));
                int isDone = cursor.getInt(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_IS_DONE));
                String date = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_DATE));
                Task task = new Task(id, title, String, isDone, date);
                taskList.add(task);
            }
        } finally {
            if (cursor != null && !(cursor.isClosed())) {
                cursor.close();
            }
        }
        return taskList;
    }


    public List<Download> getDownloadLists(){
        List<Download> downloadList = new ArrayList<>();
        String query = " SELECT * FROM Download ORDER BY download_id DESC ";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(Contract.DownloadTableFields.COLUMN_DOWNLOAD_ID));
                String url = cursor.getString(cursor.getColumnIndex(Contract.DownloadTableFields.COLUMN_URL));
                String fileName = url.substring(url.lastIndexOf("/")+1);
                int isComplete = cursor.getInt(cursor.getColumnIndex(Contract.DownloadTableFields.COLUMN_COMPLETE));
                Download download = new Download(id,fileName,isComplete);
                downloadList.add(download);
            }

        }finally {
            if (cursor!= null && !(cursor.isClosed())){
                cursor.close();
            }
        }
        return downloadList;

    }

    public List<Download> getUrlDownloadList(){
        List<Download> downloadList = new ArrayList<>();
        String query = " SELECT download_url FROM Download ";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        try{
            while (cursor.moveToNext()){
                String url = cursor.getString(cursor.getColumnIndex(Contract.DownloadTableFields.COLUMN_URL));
                Download download = new Download(url);
                downloadList.add(download);
            }
        }finally {
            if (cursor!=null && !(cursor.isClosed())){
                cursor.close();
            }
        }
        return downloadList;
    }


    public List<Task> getSpecficDateTask(String selectedDate) {
        List<Task> taskList = new ArrayList<>();
//        String selectQuery = " SELECT * FROM " + Contract.TaskTableFields.TABLE_NAME + " WHERE " +
//                Contract.TaskTableFields.COLUMN_DATE + " = " + selectedDate;
        String selectQuery = "SELECT * FROM tasks WHERE tasks_date = '" + selectedDate + "'" + " order by " + Contract.TaskTableFields.COLUMN_ID + " desc ";

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_TITLE));
                String String = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_STRING));
                int isDone = cursor.getInt(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_IS_DONE));
                String date = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_DATE));
                Task task = new Task(id, title, String, isDone, date);
                taskList.add(task);
            }
        } finally {
            if (cursor != null && !(cursor.isClosed())) {
                cursor.close();
            }
        }
        return taskList;
    }

    public void updateTaskDone (Task task){

        ContentValues values = new ContentValues();
        values.put(Contract.TaskTableFields.COLUMN_IS_DONE,task.getTaskIsDone());
        String selection = Contract.TaskTableFields.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(task.getTaskID())};
        sqLiteDatabase.update(Contract.TaskTableFields.TABLE_NAME, values , selection , selectionArgs);

    }

    public void updateTask (Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TaskTableFields.COLUMN_TITLE,task.getTaskTitle());
        contentValues.put(Contract.TaskTableFields.COLUMN_STRING,task.getTaskString());
        String selection = Contract.TaskTableFields.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(task.getTaskID())};
        sqLiteDatabase.update(Contract.TaskTableFields.TABLE_NAME,contentValues,selection,selectionArgs);
    }

    public void deleteTask (Task task){
        String selection = Contract.TaskTableFields.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(task.getTaskID())};
        sqLiteDatabase.delete(Contract.TaskTableFields.TABLE_NAME , selection , selectionArgs);
    }


    public Task getTaskSpecificId (int taskId){
        String query = " SELECT * FROM " + Contract.TaskTableFields.TABLE_NAME + " WHERE " + Contract.TaskTableFields.COLUMN_ID + " = " + taskId;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        Task task = null;
        try {
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_TITLE));
                String string = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_STRING));
                int isDone = cursor.getInt(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_IS_DONE));
                String date = cursor.getString(cursor.getColumnIndex(Contract.TaskTableFields.COLUMN_DATE));
                task = new Task(id,title,string,isDone,date);
            }
        }finally {
            if (cursor != null && !(cursor.isClosed())){
                cursor.close();
            }
        }
        return task;
    }





}



