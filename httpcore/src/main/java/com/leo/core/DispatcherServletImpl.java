package com.leo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

/**
 * 创建时，实现ApplicationContextAware接口的会自动注入spring上下文
 */
public class DispatcherServletImpl extends DispatcherServlet implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger("com.leo.ServerStart");

    @Autowired
    private ServerConfig config;
    private ApplicationContext appContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        appContext = applicationContext;
    }

    @PostConstruct
    public void initialize(){
        try{
            log.debug("模拟一个Servlet容器，加载spring mvc配置");
            MockServletContext servletContext = new MockServletContext();
            MockServletConfig servletConfig = new MockServletConfig(servletContext);
            XmlWebApplicationContext wac = new XmlWebApplicationContext();
            wac.setServletContext(servletContext);
            wac.setServletConfig(servletConfig);
            wac.setConfigLocation(config.getServletConfigLocation());
            // 使用setParent来整合Application上下文, 否则spring mvc中无法注入applicationContext中的bean
            wac.setParent(appContext);
            wac.refresh();
            // 设置DispatcherServlet的web上下文
            super.setApplicationContext(wac);
            super.init(servletConfig);
        }catch (ServletException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
