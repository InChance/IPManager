package com.leo.controller;

import com.leo.model.IPModel;
import com.leo.model.MaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * IP地址操作接口
 * @author leo
 */
@Controller
public class IPMaskController {

    @Autowired
    private IIPMaskService ipMaskService;

    /** 增加新的IP地址记录 */
    @RequestMapping( value = "/ip/collect", method = RequestMethod.POST )
    @ResponseBody
    public CommandResult collecteIp(@RequestBody IPModel model){
        return ipMaskService.addIPMask(model.getIp(), model.getName());
    }

    /** 更新IP信息 */
    @RequestMapping( value = "/ip/update", method = RequestMethod.PUT )
    @ResponseBody
    public CommandResult updateIP(@RequestBody IPModel model){
        return ipMaskService.updateIPMask(model);
    }

    /** 删除IP记录 */
    @RequestMapping( value = "/ip/delete", method = RequestMethod.DELETE )
    @ResponseBody
    public CommandResult deleteIP(@RequestBody Map<String, String> body){
        return ipMaskService.deleteIPMask(body.get("ip"));
    }

    /** 计算网段信息 */
    @RequestMapping( value = "/ip/calculate", method = RequestMethod.POST )
    @ResponseBody
    public CommandResult calculateIPMask(String ip, String mask){
        return ipMaskService.calculateIPMask(ip, mask);
    }

    /** 记录查询过的网段 */
    @RequestMapping( value = "/mask/save", method = RequestMethod.POST )
    @ResponseBody
    public CommandResult saveIPMask(@RequestBody MaskModel model){
        return ipMaskService.saveIPMask(model);
    }

    /** 列出掩码图表信息 */
    @RequestMapping( value = "/mask/chart", method = RequestMethod.GET )
    @ResponseBody
    public CommandResult getIPMaskChartInfo(){
        return ipMaskService.getIPMaskChartInfo();
    }

    /** 删除记录的网段 */
    @RequestMapping( value = "/mask/delete", method = RequestMethod.DELETE )
    @ResponseBody
    public CommandResult deleteMask(@RequestBody Map<String, String> body){
        return ipMaskService.deleteMask(body.get("mask"));
    }
}
