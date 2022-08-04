package com.lks.todolist;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class TaskDataBase extends RoomDatabase {
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
