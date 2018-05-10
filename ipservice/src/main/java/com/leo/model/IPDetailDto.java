package com.leo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/** IP详细对象 */
@Data
@AllArgsConstructor
public class IPDetailDto {
    private String ipType;      // ip地址类型
    private String netAddress;  // 网段地址
    private String broadcast;   // 广播地址
    private int    usableCount; // 可用数量
    private String firstUsable; // 第一可用
    private String lastUsable;  // 最后可用
    private int    usedCount;   // 已用数量
    private int    unusedCount; // 未用数量
}
