package com.SEIII.service;

import com.SEIII.channel.Channel;

public interface Selector<T> {

    //拥有许多Channel,Channel 可以存取消息
    // 获取channel
    Channel getChannel(String topic);
}
