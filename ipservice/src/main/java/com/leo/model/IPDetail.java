package com.leo.model;

import com.leo.manager.IIPManager;
import com.leo.service.impl.ContextService;
import com.leo.utils.IPMaskUtil;

import java.util.ArrayList;
import java.util.List;

public class IPDetail {
    private String ip;
    private String mask;
    private IPDetailDto ipDetailDto;
    private List<IPModel> listDto;

    public IPDetail(String ip, String mask) {
        this.ip = ip;
        this.mask = mask;
    }

    public IPDetailDto getIpDetailDto() {
        return ipDetailDto;
    }

    public List<IPModel> getListDto() {
        return listDto;
    }

    public IPDetail invoke() {
        // 计算网段详细信息
        ipDetailDto = IPMaskUtil.calculateIPMask(ip, mask);
        IIPManager ipManager = ContextService.getBean(IIPManager.class);
        List<IPModel> list = ipManager.getAll();
        listDto = new ArrayList<IPModel>();
        // 过滤出数据库已拥有的IP
        for (IPModel m : list) {
            if ( IPMaskUtil.checkSameSegment(ipDetailDto.getMaskAddress(), ipDetailDto.getFirstUsable(), m.getIp())) {
                listDto.add(m);
            }
        }
        ipDetailDto.setUsedCount(listDto.size());
        ipDetailDto.setUnusedCount(ipDetailDto.getUsableCount() - ipDetailDto.getUsedCount());
        return this;
    }
}