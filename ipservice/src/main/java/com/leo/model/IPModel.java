package com.leo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class IPModel {
    private String ip;
    private String name;
    private Date collectTime;

    public IPModel(){}
}