package com.leo.service;

import com.leo.model.IPMaskModel;
import com.leo.utils.ResultCode;

public interface IIPMaskService {
    /**
     * 通过IP获取实例
     * @param ip
     * @return
     */
    IPMaskModel getIPMask(String ip);

    /**
     * 添加IP
     * @return
     */
    ResultCode addIPMask(String ip, String name);

    ResultCode updateIPMask(IPMaskModel model);

    ResultCode deleteIPMask(String ip);

    /** 通过IP、掩码位计算网段信息和可用IP */
    ResultCode calculateIPMask(String ip, String mask);
}
