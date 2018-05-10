package com.leo.controller;

import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResultCode collecteIp(@RequestBody IPMaskModel model){
        return ipMaskService.addIPMask(model.getIp(), model.getName());
    }

    /** 更新IP信息 */
    @RequestMapping( value = "/ip/update", method = RequestMethod.PUT )
    @ResponseBody
    public ResultCode updateIP(@RequestBody IPMaskModel model){
        return ipMaskService.updateIPMask(model);
    }

    /** 删除IP记录 */
    @RequestMapping( value = "/ip/delete", method = RequestMethod.DELETE )
    @ResponseBody
    public ResultCode deleteIP(@RequestBody Map<String, String> body){
        return ipMaskService.deleteIPMask(body.get("ip"));
    }

    /** 计算网段信息 */
    public ResultCode calculateIPMask(String ip, String mask){

        return null;
    }
}
