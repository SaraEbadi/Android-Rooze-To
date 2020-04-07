package com.saraebadi.github.roozeto.database;
public class Contract {
    static final String CREATE_TABLE_TASK =
            " CREATE TABLE " + TaskTableFields.TABLE_NAME +
                    " ( " +
                    TaskTableFields.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    TaskTableFields.COLUMN_TITLE + " TEXT , " +
                    TaskTableFields.COLUMN_STRING + " TEXT , " +
                    TaskTableFields.COLUMN_IS_DONE + " INTEGER DEFAULT 0 , " +
                    TaskTableFields.COLUMN_DATE + " TEXT , " +
                    TaskTableFields.COLUMN_MONTH + " TEXT , " +
                    "UNIQUE ( " + TaskTableFields.COLUMN_ID + " ) ON CONFLICT REPLACE " +
                    " ) ";

    static final String CREATE_TABLE_STUDENT =
            " CREATE TABLE " + StudentTableFields.TABLE_NAME +
                    " ( " +
                    StudentTableFields.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    StudentTableFields.COLUMN_STUDENT_F_NAME + " TEXT , " +
                    StudentTableFields.COLUMN_STUDENT_L_NAME + " TEXT , " +
                    StudentTableFields.COLUMN_STUDENT_NATIONAL_CODE + " INTEGER , " +
                    StudentTableFields.COLUMN_STUDENT_GRADE + " TEXT , " +
                    "UNIQUE ( " + StudentTableFields.COLUMN_STUDENT_ID + " ) ON CONFLICT REPLACE " +
                    " ) ";

    static final String CREATE_TABLE_DOWNLOAD =
            " CREATE TABLE " + DownloadTableFields.TABLE_NAME +
                    " ( " +
                    DownloadTableFields.COLUMN_DOWNLOAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    DownloadTableFields.COLUMN_URL + " TEXT , " +
                    DownloadTableFields.COLUMN_COMPLETE + " INTEGER DEFAULT 0 , " +
                    " UNIQUE ( " + DownloadTableFields.COLUMN_DOWNLOAD_ID + " ) ON CONFLICT REPLACE , " +
                    " UNIQUE ( " + DownloadTableFields.COLUMN_URL + " ) ON CONFLICT REPLACE " +
                    " ) ";


    static final class TaskTableFields{
        static final String TABLE_NAME = "tasks";
        static final String COLUMN_ID = "tasks_id";
        static final String COLUMN_TITLE = "tasks_title";
        static final String COLUMN_STRING = "tasks_string";
        static final String COLUMN_IS_DONE = "tasks_is_done";
        static final String COLUMN_DATE = "tasks_date";
        static final String COLUMN_MONTH = "tasks_month";

    }

    static final class StudentTableFields{
        static final String TABLE_NAME = "students";
        static final String COLUMN_STUDENT_ID = "student_id";
        static final String COLUMN_STUDENT_F_NAME = "student_f_name";
        static final String COLUMN_STUDENT_L_NAME = "student_l_name";
        static final String COLUMN_STUDENT_NATIONAL_CODE = "student_national_code";
        static final String COLUMN_STUDENT_GRADE = "student_grade";
    }

    static final class DownloadTableFields{
        static final String TABLE_NAME = "Download";
        static final String COLUMN_DOWNLOAD_ID = "download_id";
        static final String COLUMN_URL = "download_url";
        static final String COLUMN_COMPLETE = "download_complete";
    }
}
