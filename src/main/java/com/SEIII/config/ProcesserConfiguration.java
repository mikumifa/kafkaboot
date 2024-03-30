package com.SEIII.config;

import com.SEIII.annotation.ProcesserBean;
import com.SEIII.channel.Channel;
import com.SEIII.service.MessageProcesser;
import com.SEIII.service.impl.StringMessageProcesser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProcesserConfiguration {
    @ProcesserBean("delete-news-processor")
    public MessageProcesser deleteChannel(@Qualifier("delete-news") Channel channel) {
        return new StringMessageProcesser(channel);
    }

    @ProcesserBean("create-news-processor")
    public MessageProcesser createchannel(@Qualifier("create-news") Channel channel) {
        return new StringMessageProcesser(channel);
    }

    @ProcesserBean("set-redis-processor")
    public MessageProcesser setRedis(@Qualifier("set-redis") Channel channel) {
        return new StringMessageProcesser(channel);
    }

}
