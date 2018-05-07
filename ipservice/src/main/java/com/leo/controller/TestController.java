package com.leo.controller;

import com.leo.model.IPMaskModel;
import com.leo.service.IIPMaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private IIPMaskService service;

    @RequestMapping("/hello")
    @ResponseBody
    public Object handleFoo() {
        IPMaskModel m = service.getIPMask(1);
        System.err.println(m);
        return m;
    }

}