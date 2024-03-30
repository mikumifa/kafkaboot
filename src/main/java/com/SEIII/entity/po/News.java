package com.SEIII.entity.po;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "doc")
public class News {
    @Id
    private String id;
    // 内容
    private String content;
    // 新闻关键词
    private List<String> keywords;
    // 媒体名称
    private String media;
    // 新闻来源
    private String source;
    // 新闻类别
    private String category;
    // html格式的内容
    private String html;
    // 文章简介
    private String intro;
    // 文章链接
    private String url;
    // 作者
    private String author;
    // 标签
    private List<String> tags;
    // 发布日期
    private String date;

    private List<String> images;

    // 新闻标题
    private String title;
    // 新闻描述
    private String description;

    // 文章 ID
    @Field(name = "doc_id")
    private String docId;




}
