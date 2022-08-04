package com.lks.todolist;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insert(Task task);

    @Update
    public void update(Task task);

    @Delete
    public void delete(Task task);

    @Query("SELECT * FROM my_tasks")
    public LiveData<List<Task>> getAllData();

    @Database(entities = Task.class, version = 2)
    abstract class TaskDataBase extends RoomDatabase {

        private static TaskDataBase instance;
        public abstract TaskDao taskDao();

        public static synchronized TaskDataBase getInstance(Context context){
            if(instance==null){
                instance = Room.databaseBuilder(context.getApplicationContext()
                                , TaskDataBase.class, "task_databse")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }
    }
}

