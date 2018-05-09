package com.leo.manager;

import com.leo.model.IPMaskModel;

/**
 * IP地址缓存管理
 */
public interface IIPMaskManager {

    IPMaskModel getByIP(String ip);

    void add(IPMaskModel model);

    void update(IPMaskModel model);

    void remove(String ip);
}
