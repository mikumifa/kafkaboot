package com.SEIII.service.impl;

import com.SEIII.service.MessageSender;
import com.SEIII.service.Selector;
import org.springframework.stereotype.Component;

/**
 * 选择其中一个channel发送
 */

@Component
public class KafkaMessageSender implements MessageSender{
    //selector负责管理channel
    private final Selector<String> selector;

    public KafkaMessageSender(Selector<String> selector) {
        this.selector = selector;
    }


    @Override
    public void send(String topic, String message) {
        selector.getChannel(topic).put(message);
    }
}
