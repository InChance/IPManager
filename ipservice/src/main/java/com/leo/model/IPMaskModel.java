package com.leo.model;

import lombok.Data;

import java.util.Date;

@Data
public class IPMaskModel {
    private String ip;
    private String name;
    private Date collectTime;
}
