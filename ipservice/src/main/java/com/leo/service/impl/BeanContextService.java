package com.leo.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
public class BeanContextService implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanContextService.context = applicationContext;
    }

    public final static <T> T getBean(Class<T> c){
        Map<String, T> map = context.getBeansOfType(c);
        Assert.isTrue(map != null && map.size() > 0, "ApplicationContext 未注册该类型bean[" + c.getSimpleName() + "]");
        Assert.isTrue(map.size() == 1, "\"ApplicationContext 注册了多个该类型bean[" + c.getSimpleName() + "]");
        return context.getBean(c);
    }
}
