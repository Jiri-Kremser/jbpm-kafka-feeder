package io.radanalytics.feeder;

import org.kie.api.event.process.*;

public class MyListener implements ProcessEventListener {

    public void beforeProcessStarted(ProcessStartedEvent processStartedEvent) {

    }

    public void afterProcessStarted(ProcessStartedEvent processStartedEvent) {

    }

    public void beforeProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

    }

    public void afterProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

    }

    public void beforeNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {
        System.out.println("Before Node triggered fooo: " + processNodeTriggeredEvent.getNodeInstance().getNodeName());
    }

    public void afterNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {

    }

    public void beforeNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {

    }

    public void afterNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {

    }

    public void beforeVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {

    }

    public void afterVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {

    }
}
