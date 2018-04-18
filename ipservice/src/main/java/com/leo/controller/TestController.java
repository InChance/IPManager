package com.leo.controller;

import com.alibaba.fastjson.JSON;
import com.leo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private IStudentService service;

    @RequestMapping("/hello")
    @ResponseBody
    public String handleFoo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "leo12");
        map.put("like", "xx");
        map.put("java", "good");
        return JSON.toJSONString(map);//service.getStudent(1);
    }

}