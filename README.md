# Kafka Message Processing Template

[中文](README_zh.md) | English

> Simple and easy-to-use high-performance template for Kafka message processing. Suggestions for modifications are
> welcome.

## Features

### 1. Simple and Easy-to-Use

Managed by Spring Boot, simply implement your business logic to handle Kafka messages effortlessly.

### 2. High Performance

Distributes messages to message queues based on topics and asynchronously processes them.

## How to Use

Suppose you need to process messages from a topic named `create-news`. Follow these steps:

#### 1. Define Topic

Define a channel in the config package, where the `@ChannelBean` annotation indicates the name of the corresponding
topic.

```java
@ChannelBean("create-news")
public Channel createChannel(){
        return new MemoryChannel();
        }
```

#### 2. Define Message Processor

Define a MessageProcessor in the config package.

```java
@ProcessorBean("create-news-processor")
public MessageProcessor createChannel(@Qualifier("create-news") Channel channel){
        return new StringMessageProcessor(channel);
        }
```

#### 3. Implement Message Receiver

Implement MessageReceiver in the service package, then bind it to the channel using the `@Sink` annotation.

```java

@Component
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
