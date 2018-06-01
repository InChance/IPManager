package com.leo.controller;

import com.leo.model.ExcelModel;
import com.leo.model.IPDetail;
import com.leo.model.IPModel;
import com.leo.service.IIPMaskService;
import com.leo.service.impl.ContextService;
import com.leo.utils.CommandResult;
import com.leo.utils.IPMaskUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel导入导出接口
 * @author leo
 */
@Controller
public class ExcelImportController {

    @Autowired
    private IIPMaskService ipMaskService;

    @RequestMapping( value = "/excel/export", method = RequestMethod.GET )
    @ResponseBody
    public Object exportDataByExcel(String ip, String mask, Integer type) throws Exception {
        // type : 0 未用， 1 已用
        if ( type == null || ( type != 0 && type != 1 ) ) {
            return CommandResult.errorResult("参数错误");
        }
        if( !IPMaskUtil.isValidIP(ip) ){
            return CommandResult.errorResult("请输入合法IP");
        }
        if( !IPMaskUtil.isValidMask(mask) ){
            return CommandResult.errorResult("请输入合法的掩码位");
        }
        IPDetail ipDetail = new IPDetail(ip, mask).invoke();
        int limit = 150000;
        List<?> list = type == 0 ? ipDetail.notUsedList(limit) : ipDetail.getListDto();
        HSSFWorkbook hwb = ExcelModel.getByList(list);
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String("ip-list.xls".getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        hwb.write(out);
        byte[] resultBytes = out.toByteArray();
        out.close();
        return new ResponseEntity<byte[]>(resultBytes, headers, HttpStatus.CREATED);
    }

}
