# kafka消息处理模版
 中文| [English](README.md)
> 简单易用的高性能，欢迎提出修改建议

## 功能特性

### 1.简单易用

使用springboot进行管理，只需完成业务实现，就能完成对于kafka的消息处理

### 2.高性能

根据topic分发消息到消息队列，并进行异步处理消息

## 如何使用

假设你需要对topic名为`create-news`的消息进行处理，你可以按照一下步骤

#### 1. 定义topic

在config包中定义channel，其中`@ChannelBean`注解表示这个channel对应的`topic`的名字

```java
    @ChannelBean("create-news")
    public Channel createchannel() {
        return new MemoryChannel();
    }
```

#### 2. 定义MessageProcesser

在config包中定义MessageProcesser

```java
  @ProcesserBean("create-news-processor")
    public MessageProcesser createchannel(@Qualifier("create-news") Channel channel) {
        return new StringMessageProcesser(channel);
    }
```

#### 3.实现MessageReceiver

在service包里MessageReceiver的实现，然后通过`@Sink`注解绑定channel

```java
@Component()
@Slf4j
@Sink(bindTo = "create-news")
@RequiredArgsConstructor
public class NewsSaveToESSink implements MessageReceiver {
    @Override
    public void process(String message) {
        log.info("NewsSaveToESSink");
    }
}

```

