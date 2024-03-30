package com.SEIII.dao;


import com.SEIII.entity.po.NewsIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



public interface NewsESDao extends ElasticsearchRepository<NewsIndex, String> {
}