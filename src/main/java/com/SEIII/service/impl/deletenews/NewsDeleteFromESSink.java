package com.SEIII.service.impl.deletenews;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.SEIII.annotation.Sink;
import com.SEIII.dao.NewsESDao;
import com.SEIII.entity.po.News;
import com.SEIII.entity.po.NewsIndex;
import com.SEIII.service.MessageReceiver;
import com.SEIII.util.JsonUtil;
import org.springframework.stereotype.Component;

@Component()
@Slf4j
@Sink(bindTo = "delete-news")
@RequiredArgsConstructor
public class NewsDeleteFromESSink implements MessageReceiver {
    private final NewsESDao newsESDao;

    @Override
    public void process(String message) {
        log.info("NewsDeleteFromESSink");
        newsESDao.deleteById(message);
    }
}
