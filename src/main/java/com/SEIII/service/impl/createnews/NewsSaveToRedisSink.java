package com.SEIII.service.impl.createnews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.SEIII.annotation.Sink;
import com.SEIII.entity.po.News;
import com.SEIII.service.MessageReceiver;
import com.SEIII.util.JsonUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

@Component()
@Slf4j
@Sink(bindTo = "create-news")
@RequiredArgsConstructor
public class NewsSaveToRedisSink implements MessageReceiver {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(String message) {
        log.info("NewsSaveToRedis");
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = new Gson().fromJson(message, type);
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    redisTemplate.opsForValue().set(String.format("news:%s:%s",  map.get("docId"), entry.getKey()), entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
