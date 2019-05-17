package com.eventsourcing.eventsourcing.command;

import java.util.UUID;

public class DeleteTask implements Task {

    private String taskName = this.getClass().getName();

    private UUID id;

    public DeleteTask(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public String getExchange() {
        return "remove.task";
    }
}
