package com.eventsourcing.eventsourcing.command;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class AddTask implements Task {
    private String taskName = this.getClass().getName();
    private UUID id;
    private String name;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    public AddTask(UUID id, String name, Date dueDate) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    public String getExchange() {
        return  "add.task";
    }

    @Override
    public String toString() {
        return "AddTask{" +
                "taskName='" + taskName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
