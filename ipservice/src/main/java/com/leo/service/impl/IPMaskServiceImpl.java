package com.leo.service.impl;

import com.leo.dao.IPMaskDao;
import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IPMaskServiceImpl implements IIPMaskService {

    @Autowired
    private IPMaskDao dao;

    @Override
    public IPMaskModel getIPMask(int id){
        return dao.get(id);
    }

}
