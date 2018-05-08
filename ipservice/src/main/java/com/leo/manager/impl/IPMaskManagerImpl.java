package com.leo.manager.impl;

import com.alibaba.druid.filter.AutoLoad;
import com.leo.dao.IPMaskDao;
import com.leo.manager.IIPMaskManager;
import com.leo.model.IPMaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IPMaskManagerImpl implements IIPMaskManager {

    // （需要改善de缓存)：key: ip, value: IPMaskModel
    private static final Map<String, IPMaskModel> IPMASK_MAP = new ConcurrentHashMap<>();

    @Autowired
    private IPMaskDao dao;

    @Override
    public IPMaskModel getByIP(String ip){
        IPMaskModel model = IPMASK_MAP.get(ip);
        if( model == null ){
            model = dao.getByIp(ip);
            if( model != null ){
                IPMASK_MAP.put(model.getIp(), model);
            }
        }
        return model;
    }

    @Override
    public void add(IPMaskModel model){
        if ( model != null ){
            dao.insert(model);
            IPMASK_MAP.put(model.getIp(), model);
        }
    }

    @Override
    public void update(IPMaskModel model){
        if( model != null ){
            dao.update(model);
            IPMASK_MAP.put(model.getIp(), model);
        }
    }

}
