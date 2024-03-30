package com.SEIII.service.impl.createnews;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.SEIII.annotation.Sink;
import com.SEIII.dao.NewsDao;
import com.SEIII.entity.po.News;
import com.SEIII.service.MessageReceiver;
import com.SEIII.util.JsonUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component()
@Slf4j
@Sink(bindTo = "create-news")
@RequiredArgsConstructor
public class NewsSaveToMongoSink implements MessageReceiver {
    private final NewsDao newsDao;

    @Override
    public void process(String message) {
        log.info("NewsSaveToMongoSink");
        News news = Objects.requireNonNull(JsonUtil.fromJson(message, News.class));
        newsDao.save(news);
    }
}
