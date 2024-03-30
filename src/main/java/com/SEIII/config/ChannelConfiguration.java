package com.SEIII.config;

import com.SEIII.annotation.ChannelBean;
import com.SEIII.channel.Channel;
import com.SEIII.channel.impl.MemoryChannel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class ChannelConfiguration {

    @ChannelBean("create-news")
    public Channel createchannel() {
        return new MemoryChannel();
    }

    @ChannelBean("delete-news")
    public Channel deletechannel() {
        return new MemoryChannel();
    }

    @ChannelBean("set-redis")
    public Channel setRedis() {
        return new MemoryChannel();
    }
}
