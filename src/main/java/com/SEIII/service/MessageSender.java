package com.SEIII.service;

/**
 * 负责发送消息，到channel
 *
 * @param <T>
 */
public interface MessageSender {

    void send(String topic, String messages);

}
