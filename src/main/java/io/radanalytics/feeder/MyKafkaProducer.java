package io.radanalytics.feeder;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Optional;
import java.util.Properties;

public class MyKafkaProducer {
    private static final String TOPIC_DEFAULT = "my-topic";
    private static final String BOOTSTRAP_SERVERS_DEFAULT = "kafka:9092";

    private static String getProperty(String camel, String snake, String defaultValue) {
        Optional<String> maybeValue = Optional.ofNullable(System.getProperty(camel));
        return maybeValue.orElseGet(() -> Optional.ofNullable(System.getenv(snake)).orElse(defaultValue));
    }

    private static String getTopic() {
        return getProperty("topic", "TOPIC", TOPIC_DEFAULT);
    }

    private static String getServers() {
        return getProperty("boostrapServers", "BOOTSTRAP_SERVERS", BOOTSTRAP_SERVERS_DEFAULT);
    }


    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "MyKafkaProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    static void runProducer(final int sendMessageCount) throws Exception {
        System.out.println("Running producer for configuration:");
        System.out.println("BOOTSTRAP_SERVERS: " + getServers());
        System.out.println("TOPIC: " + getTopic());
        final Producer<Long, String> producer = createProducer();
        long time = System.currentTimeMillis();
        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>(getTopic(), index, "Hello " + index);
                RecordMetadata metadata = producer.send(record).get();
                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
            }
        } finally {
            producer.flush();
            producer.close();
        }
    }


    public static void main(String... args) throws Exception {
        if (args.length == 0) {
            runProducer(5);
        } else {
            runProducer(Integer.parseInt(args[0]));
        }
    }

}
