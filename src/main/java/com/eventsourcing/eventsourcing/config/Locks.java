package com.eventsourcing.eventsourcing.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Locks {

    @Bean
    Boolean addTaskLock(){
        return false;
    }


    @Bean
    Boolean updateTaskLock(){
        return false;
    }
}
