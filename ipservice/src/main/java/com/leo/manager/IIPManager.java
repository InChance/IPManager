package com.leo.manager;

import com.leo.model.IPModel;

import java.util.List;

/**
 * IP地址缓存管理
 */
public interface IIPManager {

    IPModel getByIP(String ip);

    List<IPModel> getAll();

    void add(IPModel model);

    void update(IPModel model);

    void remove(String ip);
}
