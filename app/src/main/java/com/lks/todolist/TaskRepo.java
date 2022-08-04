package com.lks.todolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class TaskRepo {
    private TaskDao taskDao;
    private LiveData<List<Task>> noteList;

    public TaskRepo(Application application){
        TaskDao.TaskDataBase taskDataBase= TaskDao.TaskDataBase.getInstance(application);
        taskDao = taskDataBase.taskDao();
        noteList = taskDao.getAllData();
    }

    public void insertData(Task task){
        new insertTask(taskDao).execute(task);
    }
    public void updateData(Task task){
        new updateTask(taskDao).execute(task);
    }
    public void deleteData(Task task){
        new deleteTask(taskDao).execute(task);
    }
    public LiveData<List<Task>> getAllData(){
        return noteList;
    }

    private static class insertTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;

        public insertTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }
    private static class deleteTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;

        public deleteTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }
    private static class updateTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;

        public updateTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

}

