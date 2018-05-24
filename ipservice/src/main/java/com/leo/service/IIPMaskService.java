package com.leo.service;

import com.leo.model.IPModel;
import com.leo.model.MaskModel;
import com.leo.utils.CommandResult;

public interface IIPMaskService {
    /**
     * 通过IP获取实例
     * @param ip
     * @return
     */
    IPModel getIPMask(String ip);

    /**
     * 添加IP
     * @return
     */
    CommandResult addIPMask(String ip, String name);

    CommandResult updateIPMask(IPModel model);

    CommandResult deleteIPMask(String ip);

    /** 通过IP、掩码位计算网段信息和可用IP */
    CommandResult calculateIPMask(String ip, String mask);

    CommandResult saveIPMask(MaskModel model);

    /**获取网段的图标信息*/
    CommandResult getIPMaskChartInfo();

    CommandResult deleteMask(String mask);
}
