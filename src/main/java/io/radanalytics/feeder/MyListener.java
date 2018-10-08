package io.radanalytics.feeder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.kie.api.event.process.*;

public class MyListener implements ProcessEventListener {

    private final Producer<Long, String> producer = MyKafkaProducer.createProducer();
    private final String topic = MyKafkaProducer.getTopic();
//    ObjectMapper mapper = new ObjectMapper();

    public void beforeProcessStarted(ProcessStartedEvent processStartedEvent) {

    }

    public void afterProcessStarted(ProcessStartedEvent processStartedEvent) {

    }

    public void beforeProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

    }

    public void afterProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

    }

    public void beforeNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {
        try {
            String nodeName = processNodeTriggeredEvent.getNodeInstance().getNodeName();
//            String event = mapper.writeValueAsString(processNodeTriggeredEvent);
            System.out.println(nodeName + " entered, \n metadata-map:\n" + processNodeTriggeredEvent.getNodeInstance().getNode().getMetaData());
            final ProducerRecord<Long, String> record = new ProducerRecord<>(topic, System.currentTimeMillis(), processNodeTriggeredEvent.getNodeInstance().getNode().getMetaData().toString());
            producer.send(record);
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("Unable to send the message to Kafka topic.");
        }
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
