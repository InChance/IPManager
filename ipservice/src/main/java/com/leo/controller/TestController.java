package com.leo.controller;

import com.leo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private IStudentService service;

    @RequestMapping("/hello")
    @ResponseBody
    public String handleFoo() {
        return service.getStudent(1);
    }

}