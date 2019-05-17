package com.eventsourcing.eventsourcing.controller;

import com.eventsourcing.eventsourcing.command.*;
import com.eventsourcing.eventsourcing.model.EventSource;
import com.eventsourcing.eventsourcing.model.TaskModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {

    private final CommandHandler commandHandler;

    @Qualifier("taskEventMap")
    private final List<EventSource> taskEventDao;

    @Qualifier("taskMap")
    private final List<TaskModel> task;

    public TaskController(CommandHandler commandHandler, List<EventSource> taskEventDao, List<TaskModel> task) {
        this.commandHandler = commandHandler;
        this.taskEventDao = taskEventDao;
        this.task = task;
    }


    @PostMapping
    public void createTask(@RequestBody AddTask task){
       commandHandler.handle(task);
    }


    @PutMapping("/tasks/{id}")
    public void updateTask(@PathVariable("id")UUID id, @RequestBody UpdateTask task){
        task.setId(id);
        commandHandler.handle(task);
    }


    @PutMapping("/tasks/{id}/complete")
    public void completeTask(@PathVariable("id")UUID id){
        commandHandler.handle(new CompleteTask(id));
    }


    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable("id")UUID uuid){
        commandHandler.handle(new DeleteTask(uuid));
    }



    @GetMapping("/events")
    List<EventSource>  getTaskEvents(){
        return this.taskEventDao;
    }


    @PutMapping("/events/{id}/replay")
    void replay(@PathVariable("id")UUID uuid){
        commandHandler.handle(new ReplayEventsTask(uuid));
    }


    @GetMapping("/tasks")
    List<TaskModel>  getTasks(){
        return this.task;
    }


}
