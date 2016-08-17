package org.tmp.stats.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmp.stats.test.TestEntry;
import org.tmp.stats.test.TestRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by mf57 on 17.08.2016.
 */
@Component
public class TestListener {

    @Autowired
    TestRepository repository;

    @PostConstruct
    public void startThread() {
        new Thread(this::listen).start();
    }

    public void listen() {
        //Kafka consumer configuration settings
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer
                <>(props);

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Collections.singletonList("testTopic"));


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                repository.save(new TestEntry(record.key(), record.value()));
            }
        }
    }
}
