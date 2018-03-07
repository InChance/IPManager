package com.leo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动服务器
 */
public class ServerStart {

    private static Logger log = LoggerFactory.getLogger(ServerStart.class);

    public static void main(String[] args){
        log.debug("begin to start up server......");
        new ClassPathXmlApplicationContext("server.xml");
    }

}
