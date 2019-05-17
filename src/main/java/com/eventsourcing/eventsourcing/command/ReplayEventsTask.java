package com.eventsourcing.eventsourcing.command;

import java.util.UUID;

public class ReplayEventsTask implements Task {

    private String taskName = this.getClass().getName();
    private UUID id;

    public ReplayEventsTask(UUID id) {
        this.id = id;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getExchange() {
        return "replay.events.task";
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
