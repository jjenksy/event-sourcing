package com.eventsourcing.eventsourcing.command;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class UpdateTask implements Task  {

    private String taskName = this.getClass().getName();
    private UUID id;
    private String name;
    private String exchange = "update.task";
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;


    @Override
    public String getTaskName() {
        return taskName;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public String toString() {
        return "UpdateTask{" +
                "taskName='" + taskName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
