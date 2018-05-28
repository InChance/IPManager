package com.leo.service.impl;

import com.leo.core.ServerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.URL;
import java.util.Map;

@Component
public class ContextService implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextService.context = applicationContext;
    }

    public static String getContextPath(){
        ServerConfig serverConfig = getBean(ServerConfig.class);
        String contextName = serverConfig.getContextName();
        String configFileName = serverConfig.getServletConfigLocation().replace("classpath:", "");
        URL url = ClassUtils.getDefaultClassLoader().getResource(configFileName);
        assert url != null : "配置文件：" + configFileName + " 获取不到。";
        String path = url.getPath();
        int i = path.lastIndexOf(contextName);
        path = path.substring(1, i + 1 + contextName.length());
        return path;
    }

    public static <T> T getBean(Class<T> c){
        Map<String, T> map = context.getBeansOfType(c);
        Assert.isTrue(map != null && map.size() > 0, "ApplicationContext 未注册该类型bean[" + c.getSimpleName() + "]");
        Assert.isTrue(map.size() == 1, "\"ApplicationContext 注册了多个该类型bean[" + c.getSimpleName() + "]");
        return context.getBean(c);
    }
}
