package com.eventsourcing.eventsourcing.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomSink {

    String ADDTASK = "addtaskinput";
    String UPDATETASK = "updatetaskinput";
    String REMOVETASK = "removetaskinput";
    String COMPLETETASK = "completetaskinput";
    String REPLAYTASK = "replaytaskinput";

    @Input(ADDTASK)
    SubscribableChannel addtaskinput();

    @Input(UPDATETASK)
    SubscribableChannel updatetaskinput();


    @Input(REMOVETASK)
    SubscribableChannel removetaskinput();

    @Input(COMPLETETASK)
    SubscribableChannel completetaskinput();


    @Input(REPLAYTASK)
    SubscribableChannel replaytaskinput();
}
