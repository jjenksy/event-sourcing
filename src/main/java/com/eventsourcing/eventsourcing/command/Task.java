package com.eventsourcing.eventsourcing.command;

import java.io.Serializable;
import java.util.UUID;

public interface Task extends Serializable {
    UUID getId();
    String getExchange();
    String getTaskName();
}
