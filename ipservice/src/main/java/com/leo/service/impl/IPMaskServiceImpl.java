package com.leo.service.impl;

import com.leo.manager.IIPMaskManager;
import com.leo.model.CommandResult;
import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    /**
     * 添加IP地址入库
     * @return
     */
    @Override
    public CommandResult addIPMask(String ip, String name) {
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return ResultCode.errorResult("填写的ip或计算机名为空！");
        }
        lock.lock();
        try{
            IPMaskModel dbModel = ipMaskManager.getByIP(ip);
            if ( dbModel == null ){
                ipMaskManager.add( new IPMaskModel(ip, name, new Date()) );
                return ResultCode.succResult();
            }else{
                return ResultCode.errorResult("IP已存在！");
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * 更新IP地址
     * @param model
     * @return
     */
    @Override
    public CommandResult updateIPMask(IPMaskModel model){
        String ip = model.getIp();
        String name = model.getName();
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return ResultCode.errorResult("填写的ip或计算机名为空！");
        }
        IPMaskModel m = ipMaskManager.getByIP(ip);
        if( m == null ){
            return ResultCode.errorResult("当前ip不存在！");
        }
        ipMaskManager.update(model);
        return ResultCode.succResult("更新成功");
    }

    /**
     * 删除IP地址
     * @param ip
     * @return
     */
    @Override
    public CommandResult deleteIPMask(String ip){
        IPMaskModel m = ipMaskManager.getByIP(ip);
        if( m == null ){
            return ResultCode.errorResult("当前ip不存在！");
        }
        ipMaskManager.remove(ip);
        return ResultCode.succResult("删除成功");
    }



}
