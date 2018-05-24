package com.leo.manager.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.leo.dao.IPMaskDao;
import com.leo.manager.IIPMaskManager;
import com.leo.model.IPMaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class IPMaskManagerImpl implements IIPMaskManager {

    // key: ip, value: IPMaskModel
    private Cache<String, IPMaskModel> ipMaskCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(3, TimeUnit.DAYS).build();

    @Autowired
    private IPMaskDao dao;

    @Override
    public IPMaskModel getByIP(String ip){
        IPMaskModel model = ipMaskCache.getIfPresent(ip);
        if( model == null ){
            model = dao.getByIp(ip);
            if( model != null ){
                ipMaskCache.put(model.getIp(), model);
            }
        }
        return model;
    }

    @Override
    public void add(IPMaskModel model){
        if ( model != null ){
            dao.insert(model);
            ipMaskCache.put(model.getIp(), model);
        }
    }

    @Override
    public void update(IPMaskModel model){
        if( model != null ){
            dao.update(model);
            ipMaskCache.put(model.getIp(), model);
        }
    }

    @Override
    public void remove(String ip){
        dao.delete(ip);
        ipMaskCache.invalidate(ip); // 释放缓存
    }

}
