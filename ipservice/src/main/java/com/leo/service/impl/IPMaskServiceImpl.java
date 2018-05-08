package com.leo.service.impl;

import com.leo.manager.IIPMaskManager;
import com.leo.model.CommandResult;
import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class IPMaskServiceImpl implements IIPMaskService {

    @Autowired
    private IIPMaskManager ipMaskManager;
    @Override
    public IPMaskModel getIPMask(String ip){
        return ipMaskManager.getByIP(ip);
    }
    // 安全重入锁
    private static final Lock lock = new ReentrantLock();

    @Override
    public CommandResult addIPMask(IPMaskModel model) {
        String ip = model.getIp();
        String name = model.getName();
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return ResultCode.errorResult("ip或计算机名为空！");
        }
        lock.lock();
        try{
            IPMaskModel dbModel = ipMaskManager.getByIP(ip);
            if ( dbModel == null ){
                ipMaskManager.add(model);
                return ResultCode.succResult();
            }else{
                return ResultCode.errorResult("IP已存在！");
            }
        }finally {
            lock.unlock();
        }
    }

}
