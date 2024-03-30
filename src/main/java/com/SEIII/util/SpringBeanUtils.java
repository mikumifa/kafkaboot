package com.SEIII.util;

import lombok.Getter;
import com.SEIII.annotation.Sink;
import com.SEIII.service.MessageReceiver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component("First")
public class SpringBeanUtils implements ApplicationContextAware {
    @Getter
    public static ApplicationContext applicationContext;

    public static String getBeanName(Object bean) {
        if (applicationContext != null) {
            String[] beanNames = applicationContext.getBeanNamesForType(bean.getClass());
            for (String beanName : beanNames) {
                Object beanInstance = applicationContext.getBean(beanName);
                if (beanInstance == bean) {
                    return beanName;
                }
            }
        }
        return null;
    }

    public static List<MessageReceiver> getByBindChannelName(String channelName) {
        CopyOnWriteArrayList<MessageReceiver> res = new CopyOnWriteArrayList<>();
        if (applicationContext != null) {
            Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Sink.class);
            for (Object beanInstance : beansWithAnnotation.values()) {
                Sink sinkAnnotation = beanInstance.getClass().getAnnotation(Sink.class);
                if (sinkAnnotation != null) {
                    String bindToValue = sinkAnnotation.bindTo();
                    if (bindToValue.equals(channelName)) {
                        res.add((MessageReceiver) beanInstance);
                    }
                }
            }
        }
        return res;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }
}


