package com.leo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.net.URL;

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
            log.debug("创建Servlet容器，装载spring mvc配置");
            XmlWebApplicationContext wac = new XmlWebApplicationContext();
            // 创建Servlet配置信息
            MockServletConfig servletConfig = new MockServletConfig();
            wac.setServletConfig(servletConfig);
            wac.setConfigLocation(config.getServletConfigLocation());
            // 使用setParent来整合Spring Application上下文, 否则spring mvc中无法注入Spring Context中的bean
            wac.setParent(appContext);
            wac.refresh();
            // 设置Servlet容器上下文
            super.setApplicationContext(wac);
            super.init(wac.getServletConfig());
            // 检查配置文件
            checkConfig();
        }catch (ServletException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void checkConfig(){
        String contextName = config.getContextName();
        String configFileName = config.getServletConfigLocation().replace("classpath:", "");
        URL url = ClassUtils.getDefaultClassLoader().getResource(configFileName);
        Assert.isTrue( url != null , "配置文件：" + configFileName + " 获取不到。");
        String path = url.getPath();
        boolean contain = path.matches("\\S+/" + contextName + "/\\S+");
        Assert.isTrue( contain, ":::警告:::" + ServerConfig.class.getSimpleName() + "的属性contextName与项目根目录必须保持一致。");
    }

}
