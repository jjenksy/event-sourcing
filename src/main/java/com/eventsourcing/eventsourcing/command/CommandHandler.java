package com.eventsourcing.eventsourcing.command;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommandHandler {


    private final AmqpTemplate amqpTemplate;

    public CommandHandler(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }


    public void handle(AddTask task){
        if(task.getId()==null) task.setId(UUID.randomUUID());
        amqpTemplate.convertAndSend(task.getExchange(), UUID.randomUUID().toString(), task);
    }


    public void handle(UpdateTask task) {
        amqpTemplate.convertAndSend(task.getExchange(), UUID.randomUUID().toString(), task);
    }

    public void handle(DeleteTask deleteTask) {
        amqpTemplate.convertAndSend(deleteTask.getExchange(), UUID.randomUUID().toString(), deleteTask);

    }

    public void handle(CompleteTask completeTask) {
        amqpTemplate.convertAndSend(completeTask.getExchange(), UUID.randomUUID().toString(), completeTask);
    }

    public void handle(ReplayEventsTask replayEventsTask) {
        amqpTemplate.convertAndSend(replayEventsTask.getExchange(), UUID.randomUUID().toString(), replayEventsTask);
    }
}
