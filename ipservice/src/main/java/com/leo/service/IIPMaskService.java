package com.leo.service;

import com.leo.model.IPMaskModel;
import com.leo.utils.CommandResult;

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
    CommandResult addIPMask(String ip, String name);

    CommandResult updateIPMask(IPMaskModel model);

    CommandResult deleteIPMask(String ip);

    /** 通过IP、掩码位计算网段信息和可用IP */
    CommandResult calculateIPMask(String ip, String mask);
}
