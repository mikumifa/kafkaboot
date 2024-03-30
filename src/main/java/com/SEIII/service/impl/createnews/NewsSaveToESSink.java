package com.SEIII.service.impl.createnews;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.SEIII.annotation.Sink;
import com.SEIII.dao.NewsESDao;
import com.SEIII.entity.po.News;
import com.SEIII.entity.po.NewsIndex;
import com.SEIII.service.MessageReceiver;
import com.SEIII.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component()
@Slf4j
@Sink(bindTo = "create-news")
@RequiredArgsConstructor
public class NewsSaveToESSink implements MessageReceiver {
    private final NewsESDao newsESDao;

    @Override
    public void process(String message) {
        log.info("NewsSaveToESSink");
        NewsIndex ns = new NewsIndex();
        News news = JsonUtil.fromJson(message, News.class);
        assert news != null;
        ns.setDocId(news.getDocId());
        ns.setTitle(news.getTitle());
        ns.setContent(news.getContent());
        ns.setSource(news.getSource());
        ns.setDate(news.getDate());
        newsESDao.save(ns);
    }
}
