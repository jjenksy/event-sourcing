package com.eventsourcing.eventsourcing.events;

import com.eventsourcing.eventsourcing.command.*;

import com.eventsourcing.eventsourcing.model.EventSource;
import com.eventsourcing.eventsourcing.model.TaskModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@EnableBinding(CustomSink.class)
@Component
public class TaskEvent {

    @Qualifier("taskEventMap")
    private final List<EventSource> taskEvents;

    @Qualifier("taskMap")
    private final List<TaskModel> taskDao;

    @Autowired
    private Boolean addTaskLock;
    @Autowired
    private Boolean updateTaskLock;

    private final CommandHandler commandHandler;
    public TaskEvent(List<EventSource> taskEvents, List<TaskModel> taskDao, CommandHandler commandHandler) {
        this.taskEvents = taskEvents;
        this.taskDao = taskDao;
        this.commandHandler = commandHandler;
    }

    Logger logger = LoggerFactory.getLogger(Process.class);

    @StreamListener(CustomSink.ADDTASK)
    public void process(AddTask task) {
        if(taskExist(taskEvents, task)) {
            taskEvents.forEach(taskModel -> {
                if(task.getId().equals(taskModel.getAggregateId())){
                   taskModel.addTask(task);
                }
            });
        } else {
            EventSource eventSource = new EventSource();
            eventSource.setAggregateId(task.getId());
            eventSource.addTask(task);
            taskEvents.add(eventSource);
        }

        taskDao.add(new TaskModel(task.getId(), task.getName(), task.getDueDate()));

        logger.error("Processing task: {} ", task);
        addTaskLock = false;
    }

    private boolean taskExist(List<EventSource> taskEvents, AddTask task) {
        return taskEvents.stream().anyMatch(eventSource -> eventSource.getAggregateId().equals(task.getId()));
    }


    @StreamListener(CustomSink.UPDATETASK)
    public void process(UpdateTask task) throws InterruptedException {
        while (addTaskLock) {
            Thread.sleep(100);
        }

        addTask(task);
        taskDao.forEach(taskModel -> {
            if(task.getId().equals(taskModel.getId())){
                taskModel.setDueDate(task.getDueDate());
                taskModel.setName(task.getName());
            }
        });
        updateTaskLock = false;
    }


    @StreamListener(CustomSink.REMOVETASK)
    public void process(DeleteTask task) {
        addTask(task);
        taskDao.removeIf(taskModel -> taskModel.getId().equals(task.getId()));

    }

    @StreamListener(CustomSink.COMPLETETASK)
    public void process(CompleteTask task) throws InterruptedException {
        while (updateTaskLock) {
            Thread.sleep(100);
        }
        addTask(task);

        taskDao.forEach(taskModel -> {
            if(task.getId().equals(taskModel.getId())){
               taskModel.setComplete(task.getComplete());
            }
        });

    }


    @StreamListener(CustomSink.REPLAYTASK)
    public void process(ReplayEventsTask task) {
        Optional<EventSource> optionalEventSource = taskEvents.stream().filter(eventSource -> eventSource.getAggregateId().equals(task.getId())).findFirst();
        optionalEventSource.ifPresent(eventSource -> eventSource.getTasks().forEach(task1 -> {
            logger.error("ReplayEventsTask processing task: {} ", task1);
            if(task1.getTaskName().equalsIgnoreCase(AddTask.class.getName())) {
                //todo make process async
                commandHandler.handle((AddTask) task1);
                addTaskLock = true;
            } else if(task1.getTaskName().equalsIgnoreCase(UpdateTask.class.getName())){
                commandHandler.handle((UpdateTask) task1);
                updateTaskLock = true;
            } else if(task1.getTaskName().equalsIgnoreCase(CompleteTask.class.getName())){
                commandHandler.handle((CompleteTask) task1);
            }
        }));
        addTask(task);
    }

    private void addTask(Task task) {
        taskEvents.forEach(eventSource -> {
            if (eventSource.getAggregateId().equals(task.getId())){
                eventSource.getTasks().add(task);
            }
        });
    }

}
