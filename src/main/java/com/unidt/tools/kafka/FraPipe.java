package com.unidt.tools.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by admin on 2018/3/2.
 */
public class FraPipe {

    public static  Properties getProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.14:9092");
        props.put("acks", "all");
        props.put("retries",0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public static Producer<String, String> getProducer() {
        Properties props = getProps();
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        return producer;
    }

    public void pushData(Producer<String, String> producer, String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
        producer.send(record);
    }

    public void sendTest(){
        Producer<String, String> producer = getProducer();
        for ( int i = 0 ; i < 1000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("andatong", Integer.toString(i), Integer.toString(i));
            producer.send(record);
        }
        producer.close();
        System.out.println("OK");
    }

    public void consumeTest(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
        }
    }

    public static void main(String[] args) {
        FraPipe pipe = new FraPipe();
        pipe.sendTest();
    }

}
