package com.SEIII.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import com.SEIII.annotation.ProcesserBean;
import com.SEIII.channel.Channel;
import com.SEIII.service.MessageProcesser;
import com.SEIII.service.MessageReceiver;
import com.SEIII.util.SpringBeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@DependsOn({"First"})
public class StringMessageProcesser extends MessageProcesser {

    private final Channel channel;
    private final String channelName;

    private final ExecutorService executorService;
    private final List<MessageReceiver> receivers;


    public StringMessageProcesser(Channel channel) {
        this.channel = channel;
        channelName = SpringBeanUtils.getBeanName(channel);
        this.receivers = SpringBeanUtils.getByBindChannelName(channelName);
        executorService = Executors.newCachedThreadPool();
    }

    @PostConstruct

    public void init() {
        log.info("Starting String Message Processer %s".formatted(channelName));
        start();
    }

    @Override
    public void start() {
        new Thread(new Runner()).start();
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }

    private class Runner implements Runnable {
        @Override
        public void run() {
            while (true) {
                String message = channel.get();
                if (message != null) {
                    for (MessageReceiver sink : receivers) {
                        executorService.execute(() -> sink.process(message));
                    }
                }
            }
        }

    }

}
