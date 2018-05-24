package com.leo.manager.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.leo.dao.MaskDao;
import com.leo.manager.IMaskManager;
import com.leo.model.MaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class IMaskManagerImpl implements IMaskManager {

    private Cache<String, MaskModel> maskCache = CacheBuilder.newBuilder().softValues().build();

    @Autowired
    private MaskDao dao;

    @PostConstruct
    public void init(){
        List<MaskModel> list = dao.getAll();
        for (MaskModel m : list) {
            maskCache.put(m.getNetAddress(), m);
        }
    }

    @Override
    public MaskModel get(String netIp){
        MaskModel m = maskCache.getIfPresent(netIp);
        if ( m == null ){
            m = dao.getByNetIp(netIp);
            if (m != null){
                maskCache.put(m.getNetAddress(), m);
            }
        }
        return m;
    }

    @Override
    public void add(MaskModel model){
        if( model != null ){
            dao.insert(model);
            maskCache.put(model.getNetAddress(), model);
        }
    }

    @Override
    public List<MaskModel> getAll(){
        List<MaskModel> list = new ArrayList<MaskModel>();
        list.addAll(maskCache.asMap().values());
        return list;
    }

    @Override
    public void delete(String mask){
        if( mask != null ){
            dao.delete(mask);
            maskCache.invalidate(mask);
        }
    }
}
