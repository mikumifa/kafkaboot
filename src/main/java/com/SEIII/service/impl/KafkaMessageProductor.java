package com.SEIII.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.SEIII.channel.Channel;
import com.SEIII.config.profile.SourceConfiguration;
import com.SEIII.service.MessageProductor;
import com.SEIII.service.MessageSender;
import com.SEIII.util.SpringBeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Component
@Lazy(value = false)
@DependsOn("First")
public class KafkaMessageProductor extends MessageProductor {
    //消息发送到channelProcessor中处理
    private final MessageSender messageSender;
    private KafkaConsumer<String, String> kafkaConsumer;
    private ExecutorService executorService;
    private SourceConfiguration config;
    private List<String> topics;

    private boolean running = false;

    KafkaMessageProductor(MessageSender messageSender, SourceConfiguration config) {
        log.info("Creating Kafka");
        System.out.println("heartbeat");
        this.messageSender = messageSender;
        this.config = config;
        configure();
    }

    private static Properties getProps(String url, String group) {
        Properties props = new Properties();
        props.put("bootstrap.servers", url);
        props.put("group.id", group);


        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "30000");
        props.put("session.timeout.ms", "130000");
        props.put("auto.offset.reset", "earliest");
        props.put("max.poll.interval.ms", "120000");
        props.put("heartbeat.interval.ms", "5000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    private void configure() {
        String[] channelNames = SpringBeanUtils.getApplicationContext().getBeanNamesForType(Channel.class);
        topics = Arrays.stream(channelNames).map(channelName -> channelName.replace("channel", "")).toList();
        String url = config.getConfig().getUrl();
        String group = config.getConfig().getGroup();

        if (topics.isEmpty() || StringUtils.isEmpty(url) || StringUtils.isEmpty(group)) {
            throw new IllegalArgumentException("Kafka configuration is incomplete");
        }

        Properties props = getProps(url, group);
        this.kafkaConsumer = new KafkaConsumer<>(props);

    }

    @Override
    public void start() {
        running = true;
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new KafkaRunner());
        super.start();
    }

    @Override
    public void stop() {
        running = false;
        executorService.shutdownNow();
        super.stop();
    }

    @PostConstruct
    public void init() {
        log.info("Starting Kafka");
        start();
    }

    private class KafkaRunner implements Runnable {

        @Override
        public void run() {
            kafkaConsumer.subscribe(topics);
            while (running) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(40));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset() + " topic: " + record.topic());
                    messageSender.send(record.topic(), record.value());
                }
            }
            kafkaConsumer.unsubscribe();
        }
    }
}
