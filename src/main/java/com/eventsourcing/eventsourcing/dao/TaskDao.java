package com.eventsourcing.eventsourcing.dao;

import com.eventsourcing.eventsourcing.model.EventSource;
import com.eventsourcing.eventsourcing.model.TaskModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class TaskDao {


    @Bean(name = "taskEventMap")
    public List<EventSource> taskDao(){
        return new LinkedList<>();
    }


    @Bean(name = "taskMap")
    public List<TaskModel> task(){
        return new ArrayList<>();
    }



}
