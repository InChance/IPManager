package com.leo.controller;

import com.leo.model.ExcelModel;
import com.leo.model.IPDetail;
import com.leo.utils.CommandResult;
import com.leo.utils.IPMaskUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel导入导出接口
 * @author leo
 */
@Controller
public class ExcelImportController {

    @RequestMapping( value = "/excel/export", method = RequestMethod.GET )
    @ResponseBody
    public Object exportDataByExcel(HttpServletRequest request, String ip, String mask, Integer type) throws Exception {
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
        //处理下载显示的文件名，解决不同浏览器的问题
        String name = "ip-list.xls";
        String downloadFielName = new String(name.getBytes("UTF-8"),"iso-8859-1");
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && agent.contains("MSIE") || null != agent && agent.contains("Trident")) {// ie
            downloadFielName = URLEncoder.encode(name, "UTF-8");
        }
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        hwb.write(out);
        byte[] resultBytes = out.toByteArray();
        out.close();
        // HttpStatus.OK ie不支持状态码为201(HttpStatus.CREATED)的下载，要返回200
        return new ResponseEntity<byte[]>(resultBytes, headers, HttpStatus.OK);
    }

}
