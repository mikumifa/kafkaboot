package com.SEIII.service.impl;

import com.SEIII.channel.Channel;
import com.SEIII.service.Selector;
import com.SEIII.util.SpringBeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//轮训
@Component
@DependsOn("First")
public class KafkaSelector implements Selector {
    //下一个channel的index
    private final AtomicInteger next = new AtomicInteger(0);
    private final Map<String, Channel> channels;

    KafkaSelector(List<Channel> channels) {
        this.channels = new ConcurrentHashMap<>();
        for (var channel : channels) {
            this.channels.put(SpringBeanUtils.getBeanName(channel), channel);
        }
    }


    @Override
    public Channel getChannel(String topic) {
        return channels.get(topic);
    }
}
