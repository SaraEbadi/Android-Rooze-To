package com.saraebadi.github.roozeto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQliteOpenHelper extends SQLiteOpenHelper{
    static final String DATABASE_NAME = "task_database.db";
    static final int VERSION_NUMBER = 13;

    public DatabaseSQliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.CREATE_TABLE_TASK);
        db.execSQL(Contract.CREATE_TABLE_STUDENT);
        db.execSQL(Contract.CREATE_TABLE_DOWNLOAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Contract.TaskTableFields.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + Contract.StudentTableFields.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.DownloadTableFields.TABLE_NAME);
        onCreate(db);
    }
}







