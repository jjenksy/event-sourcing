package com.eventsourcing.eventsourcing.command;

import java.util.UUID;

public class CompleteTask implements Task {

    private String taskName = this.getClass().getName();
    private UUID id;
    public CompleteTask(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Boolean getComplete() {
        return true;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public String getExchange() {
        return "complete.task";
    }
}
