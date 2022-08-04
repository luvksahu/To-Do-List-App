package com.lks.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lks.todolist.Classes.RecyclerItemClickListener;
import com.lks.todolist.databinding.ActivityMainBinding;
import com.lks.todolist.databinding.DialogDesignBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Dialog dialog;
    private TaskViewModel taskViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider
                .AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(TaskViewModel.class);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertData.class);
                intent.putExtra("type", "add");
                startActivityForResult(intent, 1);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        RvAdapter adapter = new RvAdapter();
        binding.recyclerView.setAdapter(adapter);

        taskViewModel.getAllNotes().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.submitList(tasks);
            }
        });

        binding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (MainActivity.this, binding.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog_design);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
                        dialog.show();

                        dialog.findViewById(R.id.btnDecrease).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String taskName = adapter.getTask(position).getTaskName();
                                int taskInitVal = Integer.parseInt(adapter.getTask(position).getTaskInitVal())-1;
                                String taskFinalVal = adapter.getTask(position).getTaskFinalVal();
                                if(taskInitVal>=0){
                                    Task task = new Task(taskName, taskInitVal+"", taskFinalVal);
                                    task.setId(adapter.getTask(position).getId());
                                    taskViewModel.update(task);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Progress cannot be less than 0", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setIcon(R.drawable.ic_warning)
                                        .setTitle("Delete Task")
                                        .setMessage("Are you sure you want to delete this task?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                taskViewModel.delete(adapter.getTask(position));
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        });

                        dialog.findViewById(R.id.btnIncrease).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String taskName = adapter.getTask(position).getTaskName();
                                int taskInitVal = Integer.parseInt(adapter.getTask(position).getTaskInitVal())+1;
                                String taskFinalVal = adapter.getTask(position).getTaskFinalVal();
                                if(taskInitVal <= Integer.parseInt(taskFinalVal)){
                                    Task task = new Task(taskName, taskInitVal+"", taskFinalVal);
                                    task.setId(adapter.getTask(position).getId());
                                    taskViewModel.update(task);
//                                adapter.notifyItemChanged(position);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Progress can't exceed "+taskFinalVal, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, InsertData.class);
                        intent.putExtra("type", "update");
                        intent.putExtra("taskName", adapter.getTask(position).getTaskName());
                        intent.putExtra("taskInitVal", adapter.getTask(position).getTaskInitVal());
                        intent.putExtra("taskFinalVal", adapter.getTask(position).getTaskFinalVal());
                        intent.putExtra("id", adapter.getTask(position).getId());
                        startActivityForResult(intent, 2);
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){

            String taskName = data.getStringExtra("taskName");
            String taskInitVal = data.getStringExtra("taskInitVal");
            String taskFinalVal = data.getStringExtra("taskFinalVal");
            Task task = new Task(taskName, taskInitVal, taskFinalVal);
            taskViewModel.insert(task);
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();

        }
        else if(requestCode==2){
            String taskName = data.getStringExtra("taskName");
            String taskInitVal = data.getStringExtra("taskInitVal");
            String taskFinalVal = data.getStringExtra("taskFinalVal");
            Task task = new Task(taskName, taskInitVal, taskFinalVal);
            task.setId(data.getIntExtra("id", 0));
            taskViewModel.update(task);
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Exit App")
                .setMessage("Do you want to exit this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}