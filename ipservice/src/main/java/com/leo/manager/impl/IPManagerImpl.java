package com.leo.manager.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.leo.dao.IPDao;
import com.leo.manager.IIPManager;
import com.leo.model.IPModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class IPManagerImpl implements IIPManager {

    // key: ip, value: IPModel
    private Cache<String, IPModel> ipCache = CacheBuilder.newBuilder().softValues().build();

    @Autowired
    private IPDao dao;

    @PostConstruct
    public void init(){
        List<IPModel> list = dao.getAll();
        for (IPModel m : list) {
            ipCache.put(m.getIp(), m);
        }
    }

    @Override
    public IPModel getByIP(String ip){
        IPModel model = ipCache.getIfPresent(ip);
        if( model == null ){
            model = dao.getByIp(ip);
            if( model != null ){
                ipCache.put(model.getIp(), model);
            }
        }
        return model;
    }

    @Override
    public List<IPModel> getAll(){
        List<IPModel> list = new ArrayList<IPModel>();
        list.addAll(ipCache.asMap().values());
        return list;
    }

    @Override
    public void add(IPModel model){
        if ( model != null ){
            dao.insert(model);
            ipCache.put(model.getIp(), model);
        }
    }

    @Override
    public void update(IPModel model){
        if( model != null ){
            dao.update(model);
            ipCache.put(model.getIp(), model);
        }
    }

    @Override
    public void remove(String ip){
        dao.delete(ip);
        ipCache.invalidate(ip); // 释放缓存
    }

}
