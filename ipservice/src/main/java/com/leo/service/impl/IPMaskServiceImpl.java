package com.leo.service.impl;

import com.leo.manager.IIPMaskManager;
import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.IPMaskUtil;
import com.leo.utils.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class IPMaskServiceImpl implements IIPMaskService {

    @Autowired
    private IIPMaskManager ipMaskManager;

    // 安全重入锁
    private static final Lock lock = new ReentrantLock();

    @Override
    public IPMaskModel getIPMask(String ip){
        return ipMaskManager.getByIP(ip);
    }

    /** 添加IP地址入库 */
    @Override
    public CommandResult addIPMask(String ip, String name) {
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return CommandResult.errorResult("填写的ip或计算机名为空！");
        }
        if(IPMaskUtil.isValidIP(ip)){
            return CommandResult.errorResult("请输入合法IP");
        }
        lock.lock();
        try{
            IPMaskModel dbModel = ipMaskManager.getByIP(ip);
            if ( dbModel == null ){
                ipMaskManager.add( new IPMaskModel(ip, name, new Date()) );
                return CommandResult.succResult();
            }else{
                return CommandResult.errorResult("IP已存在！");
            }
        }finally {
            lock.unlock();
        }
    }

    /** 更新IP地址 */
    @Override
    public CommandResult updateIPMask(IPMaskModel model){
        String ip = model.getIp();
        String name = model.getName();
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return CommandResult.errorResult("填写的ip或计算机名为空！");
        }
        if(IPMaskUtil.isValidIP(ip)){
            return CommandResult.errorResult("请输入合法IP");
        }
        IPMaskModel m = ipMaskManager.getByIP(ip);
        if( m == null ){
            return CommandResult.errorResult("当前ip不存在！");
        }
        ipMaskManager.update(model);
        return CommandResult.succResult("更新成功");
    }

    /** 删除IP地址 */
    @Override
    public CommandResult deleteIPMask(String ip){
        if(IPMaskUtil.isValidIP(ip)){
            return CommandResult.errorResult("请输入合法IP");
        }
        IPMaskModel m = ipMaskManager.getByIP(ip);
        if( m == null ){
            return CommandResult.errorResult("当前ip不存在！");
        }
        ipMaskManager.remove(ip);
        return CommandResult.succResult("删除成功");
    }

    @Override
    public CommandResult calculateIPMask(String ip, String mask){
        if( !IPMaskUtil.isValidIP(ip) ){
            return CommandResult.errorResult("请输入合法IP");
        }
        if( !IPMaskUtil.isValidMask(mask) ){
            return CommandResult.errorResult("请输入合法的掩码位");
        }

        return null;
    }

}
