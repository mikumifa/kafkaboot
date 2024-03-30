package com.SEIII.channel.impl;

import com.SEIII.channel.Channel;
import org.springframework.context.annotation.Lazy;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public class MemoryChannel implements Channel {

    //  阻塞队列,在队列为空时，获取元素的线程会等待队列变为非空
    private final Queue<String> messageQueue;

    public MemoryChannel() {
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    @Override
    @Lazy(false)
    public String get() {
        return messageQueue.poll();
    }

    @Override
    @Lazy(false)
    public void put(String message) {
        messageQueue.offer(message);
    }


}
