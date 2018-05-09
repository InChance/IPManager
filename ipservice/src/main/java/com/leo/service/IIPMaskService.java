package com.leo.service;

import com.leo.model.CommandResult;
import com.leo.model.IPMaskModel;

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
}
