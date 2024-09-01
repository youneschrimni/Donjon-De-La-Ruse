package sampledata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;

    // Constructor
    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    // ViewHolder class
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView descriptionTextView;
        public TextView dueDateTextView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.taskDescription);
            dueDateTextView = itemView.findViewById(R.id.taskDueDate);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);
        holder.descriptionTextView.setText(currentTask.getDescription());
        holder.dueDateTextView.setText(currentTask.getDueDateString());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
