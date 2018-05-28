package com.leo.controller;

import com.leo.service.impl.ContextService;
import com.leo.utils.CommandResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Excel导入导出接口
 * @author leo
 */
@Controller
public class ExcelImportController {

    @RequestMapping( value = "/excel/import", method = RequestMethod.POST )
    @ResponseBody
    public CommandResult importDataByExcel(HttpServletRequest request){
        String rootPath = ContextService.getContextPath();
        System.err.println("rootPath:"+ rootPath);
        return CommandResult.errorResult(rootPath);
    }

}
