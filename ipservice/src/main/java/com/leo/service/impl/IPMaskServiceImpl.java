package com.leo.service.impl;

import com.leo.manager.IIPManager;
import com.leo.manager.IMaskManager;
import com.leo.model.IPDetail;
import com.leo.model.IPDetailDto;
import com.leo.model.IPModel;
import com.leo.model.MaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.CommandResult;
import com.leo.utils.IPMaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class IPMaskServiceImpl implements IIPMaskService {

    @Autowired
    private IIPManager ipManager;

    @Autowired
    private IMaskManager maskManager;

    // 数据库写入锁
    private static final Lock lock = new ReentrantReadWriteLock().writeLock();

    @Override
    public IPModel getIPMask(String ip){
        return ipManager.getByIP(ip);
    }

    /** 添加IP地址入库 */
    @Override
    public CommandResult addIPMask(String ip, String name) {
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return CommandResult.errorResult("填写的ip或计算机名为空！");
        }
        if(!IPMaskUtil.isValidIP(ip)){
            return CommandResult.errorResult("请输入合法IP");
        }
        try{
            lock.lock();
            IPModel dbModel = ipManager.getByIP(ip);
            if ( dbModel == null ){
                ipManager.add( new IPModel(ip, name, new Date()) );
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
    public CommandResult updateIPMask(IPModel model){
        String ip = model.getIp();
        String name = model.getName();
        if (ip == null || "".equals(ip) || name == null || "".equals(name)) {
            return CommandResult.errorResult("填写的ip或计算机名为空！");
        }
        if(!IPMaskUtil.isValidIP(ip)){
            return CommandResult.errorResult("请输入合法IP");
        }
        try{
            lock.lock();
            IPModel m = ipManager.getByIP(ip);
            if( m == null ){
                return CommandResult.errorResult("当前ip不存在！");
            }
            ipManager.update(model);
            return CommandResult.succResult("更新成功");
        }finally {
            lock.unlock();
        }
    }

    /** 删除IP地址 */
    @Override
    public CommandResult deleteIPMask(String ip){
        if(!IPMaskUtil.isValidIP(ip)){
            return CommandResult.errorResult("请输入合法IP");
        }
        try{
            lock.lock();
            IPModel m = ipManager.getByIP(ip);
            if( m == null ){
                return CommandResult.errorResult("当前ip不存在！");
            }
            ipManager.remove(ip);
            return CommandResult.succResult("删除成功");
        }finally {
            lock.unlock();
        }
    }

    /** 计算网段详细信息 */
    @Override
    public CommandResult calculateIPMask(String ip, String mask){
        if( !IPMaskUtil.isValidIP(ip) ){
            return CommandResult.errorResult("请输入合法IP");
        }
        if( !IPMaskUtil.isValidMask(mask) ){
            return CommandResult.errorResult("请输入合法的掩码位");
        }
        IPDetail ipDetail = new IPDetail(ip, mask).invoke();
        boolean isRecord = maskManager.get( ipDetail.getIpDetailDto().getNetAddress() ) != null;
        ipDetail.setIsRecord(isRecord ? 1 : 0);
        return CommandResult.succResult(ipDetail);
    }

    /** 保存搜索的网段信息 */
    @Override
    public CommandResult saveIPMask(MaskModel model){
         if(!IPMaskUtil.isValidIP(model.getNetAddress())){
            return CommandResult.errorResult("请输入合法的网段");
        }
        if(!IPMaskUtil.isValidMask(model.getMaskAddress())){
            return CommandResult.errorResult("请输入合法的子网掩码");
        }
        try{
            lock.lock();
            IPDetail ipDetail = new IPDetail(model.getNetAddress(), model.getMaskAddress()).invoke();
            if( maskManager.get( ipDetail.getIpDetailDto().getNetAddress() ) != null ){
                return CommandResult.errorResult("该网段已被记录");
            }
            model.setRecordTime(new Date());
            maskManager.add(model);
            return CommandResult.succResult();
        }finally {
            lock.unlock();
        }
    }

    /** 获取网段的图标信息 */
    @Override
    public CommandResult getIPMaskChartInfo(){
        List<MaskModel> list = maskManager.getAll();
        List<Map<String, Object>> listDto = new ArrayList<Map<String, Object>>();
        for ( MaskModel m : list ) {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            IPDetail ipDetail = new IPDetail(m.getNetAddress(), m.getMaskAddress()).invoke();
            IPDetailDto ipDetailDto = ipDetail.getIpDetailDto();
            map.put("netIp", ipDetailDto.getNetAddress());
            map.put("mask", IPMaskUtil.getMaskbitByMaskAddress(ipDetailDto.getMaskAddress()));
            map.put("usedNum", ipDetailDto.getUsedCount());
            map.put("unusedNum", ipDetailDto.getUnusedCount());
            listDto.add(map);
        }
        return CommandResult.succResult(listDto);
    }

    /** 删除子网掩码 */
    @Override
    public CommandResult deleteMask(String mask){
        if (!IPMaskUtil.isValidMask(mask)){
            return CommandResult.errorResult("非法的子网掩码");
        }
        try{
            lock.lock();
            if( maskManager.get( mask ) == null ){
                return CommandResult.errorResult("该网段未被记录");
            }
            maskManager.delete(mask);
            return CommandResult.succResult();
        }finally {
            lock.unlock();
        }
    }

}
