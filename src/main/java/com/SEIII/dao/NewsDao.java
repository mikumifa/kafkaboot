package com.SEIII.dao;

import com.SEIII.entity.po.News;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface NewsDao extends MongoRepository<News, String> {
    // 字符串比较日期
    @Query("{$and: [{date: {$gte: ?0}}, {date: {$lte: ?1}}]}")
    Page<News> findByDateBetween(String startTime, String endTime, Pageable pageable);

    void deleteByDocId(String docId);

    News findByDocId(String docId);
    @Query("{docId : ?0}")
    void updateByDocId(String docId, News news);
}
