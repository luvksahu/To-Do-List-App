package com.lks.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_tasks")
public class Task {
    private String taskName;
    private String taskInitVal;
    private String taskFinalVal;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task(String taskName, String taskInitVal, String taskFinalVal) {
        this.taskName = taskName;
        this.taskInitVal = taskInitVal;
        this.taskFinalVal = taskFinalVal;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskInitVal() {
        return taskInitVal;
    }

    public void setTaskInitVal(String taskInitVal) {
        this.taskInitVal = taskInitVal;
    }

    public String getTaskFinalVal() {
        return taskFinalVal;
    }

    public void setTaskFinalVal(String taskFinalVal) {
        this.taskFinalVal = taskFinalVal;
    }
}

