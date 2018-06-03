package com.leo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class IPModel {
    private String ip;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date collectTime;

    public IPModel(){}
}
