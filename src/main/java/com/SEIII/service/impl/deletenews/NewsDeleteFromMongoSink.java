package com.SEIII.service.impl.deletenews;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.SEIII.annotation.Sink;
import com.SEIII.dao.NewsDao;
import com.SEIII.service.MessageReceiver;
import org.springframework.stereotype.Component;

@Component()
@Slf4j
@Sink(bindTo = "delete-news")
@RequiredArgsConstructor
public class NewsDeleteFromMongoSink implements MessageReceiver {

    private final NewsDao newsDao;

    @Override
    public void process(String message) {
        log.info("NewsDeleteFromMongoSink");
        newsDao.deleteByDocId(message);
    }
}
