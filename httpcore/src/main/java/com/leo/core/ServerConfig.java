package com.leo.core;

import lombok.Data;

@Data
public class ServerConfig {

    private int port;
    private String charset;
    private String servletConfigLocation;

}
