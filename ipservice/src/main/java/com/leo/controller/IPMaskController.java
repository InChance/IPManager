package com.leo.controller;

import com.leo.model.CommandResult;
import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import com.leo.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * IP地址操作接口
 * @author leo
 */
@Controller
public class IPMaskController {

    @Autowired
    private IIPMaskService ipMaskService;

    @RequestMapping(value = "/ip/collect", method = RequestMethod.POST)
    @ResponseBody
    public CommandResult collecteIp(@RequestBody IPMaskModel model){
        return ipMaskService.addIPMask(model);
    }



}
