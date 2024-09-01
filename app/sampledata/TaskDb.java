package sampledata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaskDb extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todos";
    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DUE_DATE = "due_date";
    public TaskDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ask a question about changing data type and use builtin DATE
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DUE_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS); // This could be better, but enough for a test run
        onCreate(db);
    }

    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_DUE_DATE, task.getDueDate().toString()); // Convert Date to String
        long newId = db.insert(TABLE_TASKS, null, values);
        db.close();
        return newId;
    }

    private Task taskFromCursor(Cursor cursor) {
        int taskId = -1;
        try {
            taskId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));
            Instant dueDate = Instant.parse(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DUE_DATE)));
            return new Task(taskId, description, dueDate);
        } catch (Exception e) {
            Log.e("TODO", "Reading record ("+taskId+") failed");
            return null;
        }
    }
    public Task getTaskById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            Task t = this.taskFromCursor(cursor);
            cursor.close();
            return t;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS + " ORDER BY " + KEY_DUE_DATE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Task task = this.taskFromCursor(cursor);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return taskList;
    }

    public void removeTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}