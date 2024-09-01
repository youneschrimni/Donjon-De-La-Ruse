package sampledata;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Task {
    private long id;
    private final String description;
    private final Instant dueDate;

    public Task(long id, String description, Instant dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
    }
    public Task(String description, Instant dueDate) {
        this.id = -1;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Task(String description, String dueDateText) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.id = -1;
        this.description = description;
        Instant dd;
        try {
            LocalDate date = LocalDate.parse(dueDateText, dateFormatter);
            dd = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            dd = Instant.now();
        }
        this.dueDate = dd;
    }

    public long getId() {
        return id;
    }
    public void setId(long newId) {
        id = newId;
    }

    public String getDescription() {
        return description;
    }

    public Instant getDueDate() {
        return dueDate;
    }
    public String getDueDateString() {
        return dueDate.atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

}