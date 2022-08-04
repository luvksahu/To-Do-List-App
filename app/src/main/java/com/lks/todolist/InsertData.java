package com.lks.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lks.todolist.databinding.ActivityInsertDataBinding;

public class InsertData extends AppCompatActivity {

    ActivityInsertDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type=getIntent().getStringExtra("type");
        if(type.equals("update")){
            setTitle("Update Task");
            binding.etTaskName.setText(getIntent().getStringExtra("taskName"));
            binding.etTaskInitVal.setText(getIntent().getStringExtra("taskInitVal"));
            binding.etTaskFinalVal.setText(getIntent().getStringExtra("taskFinalVal"));
            int id = getIntent().getIntExtra("id", 0);
            binding.btnSaveTask.setText("Update Task");
            binding.btnSaveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(binding.etTaskName.getText().toString().equals(""))
                        binding.etTaskName.setError("Please enter tittle");
                    else if(binding.etTaskInitVal.getText().toString().equals("")){
                        binding.etTaskInitVal.setError("Please enter initial value for task");
                    }
                    else if(binding.etTaskFinalVal.getText().toString().equals("")){
                        binding.etTaskFinalVal.setError("Please enter final value for task");
                    }
                    else{
                        new AlertDialog.Builder(InsertData.this)
                                .setIcon(R.drawable.ic_warning)
                                .setTitle("Update Task")
                                .setMessage("Do you want to update this task?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent();
                                        intent.putExtra("taskName", binding.etTaskName.getText().toString());
                                        intent.putExtra("taskInitVal", binding.etTaskInitVal.getText().toString());
                                        intent.putExtra("taskFinalVal", binding.etTaskFinalVal.getText().toString());
                                        intent.putExtra("id", id);
                                        setResult(RESULT_OK, intent);
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
            });
        }
        else{
            setTitle("Add Task");
            binding.btnSaveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(binding.etTaskName.getText().toString().equals("")) {
                        binding.etTaskName.setError("Please enter task name");
                    }
                    else if(binding.etTaskInitVal.getText().toString().equals("")){
                        binding.etTaskInitVal.setError("Please enter initial value for task");
                    }
                    else if(binding.etTaskFinalVal.getText().toString().equals("")){
                        binding.etTaskFinalVal.setError("Please enter final value for task");
                    }
                    else if(Integer.parseInt(binding.etTaskInitVal.getText().toString())
                            >Integer.parseInt(binding.etTaskFinalVal.getText().toString())){
                        binding.etTaskInitVal.setError("Enter value less than final value");
                    }
                    else{
                        Intent intent = new Intent();
                        intent.putExtra("taskName", binding.etTaskName.getText().toString());
                        intent.putExtra("taskInitVal", binding.etTaskInitVal.getText().toString());
                        intent.putExtra("taskFinalVal", binding.etTaskFinalVal.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(InsertData.this, MainActivity.class));
    }
}