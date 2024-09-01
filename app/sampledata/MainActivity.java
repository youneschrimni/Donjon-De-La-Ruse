package sampledata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TaskDb taskDb = null;
    List<Task> taskList = null;
    TaskAdapter taskAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.taskList);
        try (TaskDb db = new TaskDb(this)) {
            taskDb = db;
            taskList = db.getAllTasks();
            if (taskList.isEmpty()) {
                db.addTask(new Task("Add task", Instant.now()));
                taskList=db.getAllTasks();
            }
            taskAdapter = new TaskAdapter(taskList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(taskAdapter);
        } catch (Exception e) {
            Log.e("TODO", "Opening database failed");
            Log.e("TODO",e.getMessage());
        }
    }
    public void addTask(View view) {
        EditText taskEditText = findViewById(R.id.taskEditText);
        EditText datePickerButton = findViewById(R.id.datePickerButton);
        String taskText = taskEditText.getText().toString().trim();
        String dueDateText = datePickerButton.getText().toString().trim();
        Task newTask = null;
        if (!taskText.isEmpty()) {
            if (!dueDateText.isEmpty()) {
                newTask = new Task(taskText, dueDateText);
            } else {
                newTask = new Task(taskText, Instant.now());
            }
        }
        if (newTask != null) {
            long newId = taskDb.addTask(newTask);
            newTask.setId(newId);
            taskList.add(newTask);
            taskAdapter.notifyItemInserted(taskList.size() - 1);
        }
    }
}