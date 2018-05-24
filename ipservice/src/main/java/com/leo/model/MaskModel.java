package com.leo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MaskModel {
    private String netAddress;
    private String maskAddress;
    private Date recordTime;

    public MaskModel(){}
}
