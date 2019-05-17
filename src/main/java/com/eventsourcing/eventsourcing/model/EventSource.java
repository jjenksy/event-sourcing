package com.eventsourcing.eventsourcing.model;

import com.eventsourcing.eventsourcing.command.Task;

import java.util.Stack;
import java.util.UUID;

public class EventSource {

    private UUID aggregateId;
    private Stack<Task> tasks = new Stack<>();

    public UUID getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }



    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Stack<Task> getTasks() {
        return tasks;
    }
}
