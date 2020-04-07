package com.saraebadi.github.roozeto.model;

public class Task {
    private int taskID;
    private String taskTitle;
    private String taskString;
    private int taskIsDone;

    public String getTaskString() {
        return taskString;
    }

    public void setTaskString(String taskString) {
        this.taskString = taskString;
    }

    private String taskDate;

    public Task(int taskID, String taskTitle,String taskString, int taskIsDone, String taskDate) {
        this.taskID = taskID;
        this.taskTitle = taskTitle;
        this.taskString = taskString;
        this.taskIsDone = taskIsDone;
        this.taskDate = taskDate;
    }

    public Task(String taskTitle,String taskString, int taskIsDone, String taskDate) {
        this.taskTitle = taskTitle;
        this.taskString = taskString;
        this.taskIsDone = taskIsDone;
        this.taskDate = taskDate;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public int getTaskIsDone() {
        return taskIsDone;
    }

    public void setTaskIsDone(int taskIsDone) {
        this.taskIsDone = taskIsDone;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }
}
