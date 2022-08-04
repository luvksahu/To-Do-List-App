package com.lks.todolist;


import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lks.todolist.Classes.RecyclerItemClickListener;
import com.lks.todolist.databinding.ActivityMainBinding;
import com.lks.todolist.databinding.DialogDesignBinding;
import com.lks.todolist.databinding.RvItemDesignBinding;

public class RvAdapter extends ListAdapter<Task,RvAdapter.ViewHolder> {

    public RvAdapter() {
        super(CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Task> CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTaskName().equals(newItem.getTaskName())
                    && oldItem.getTaskInitVal().equals(newItem.getTaskInitVal())
                    && oldItem.getTaskFinalVal().equals(newItem.getTaskFinalVal());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Task task = getItem(position);
        holder.binding.tvTaskName.setText(task.getTaskName());
        holder.binding.tvTaskInitValue.setText(task.getTaskInitVal());
        holder.binding.tvTaskFinalValue.setText(task.getTaskFinalVal());
        holder.binding.progressBar.setProgress(Integer.parseInt(task.getTaskInitVal()));
        holder.binding.progressBar.setMax(Integer.parseInt(task.getTaskFinalVal()));


    }
    public Task getTask(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RvItemDesignBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvItemDesignBinding.bind(itemView);
        }
    }
}

