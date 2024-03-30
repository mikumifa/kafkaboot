package com.SEIII.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


@Document(indexName = "sina_news_item", createIndex = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsIndex {
    /**
     * index:是否设置分词  默认为true
     * analyzer：储存时使用的分词器
     * searchAnalyze:搜索时使用的分词器
     * store：是否存储  默认为false
     * type：数据类型  默认值是FieldType.Auto
     */
    @Id
    @Field(name = "doc_id")
    private String docId;
    @Field(name = "title_content")
    private String title;
    private String date;
    private String source;
    private String content;
}
