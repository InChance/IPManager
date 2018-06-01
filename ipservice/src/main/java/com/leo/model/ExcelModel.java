package com.leo.model;

import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelModel {
    @Getter
    private List<SheetModel> smList;

    public ExcelModel(InputStream ips, String fileName) throws IOException {
        boolean isXSS = fileName.contains(".xlsx");
        Workbook wb = isXSS ? new XSSFWorkbook(ips) : new HSSFWorkbook(ips);
        smList = new ArrayList<SheetModel>();
        int sheetSize = wb.getNumberOfSheets();
        for (int index = 0; index < sheetSize; index++) {
            SheetModel sm = new SheetModel();
            Sheet sheet = wb.getSheetAt(index);
            for ( Iterator iRow = sheet.rowIterator(); iRow.hasNext(); ) {
                Row row = isXSS ? (XSSFRow)iRow.next() : (HSSFRow)iRow.next();
                LineModel lm = new LineModel();
                for( Iterator iCell = row.cellIterator(); iCell.hasNext(); ){
                    Cell cell = isXSS ? (XSSFCell)iCell.next() : (HSSFCell)iCell.next();
                    lm.push(cell.getStringCellValue());
                }
                sm.push(lm);
            }
            smList.add(sm);
        }
    }

    private class SheetModel{
        @Getter
        private List<LineModel> lines;

        private SheetModel(){
            lines = new ArrayList<LineModel>();
        }

        private void push(LineModel lm){
            lines.add(lm);
        }
    }

    private class LineModel{
        @Getter
        private List<String> columns;

        private LineModel(){
            columns = new ArrayList<String>();
        }

        private void push(String content){
            columns.add(content);
        }
    }

    /**
     * List转换成Excel数据
     * @param list
     * @return
     */
    public static <T> HSSFWorkbook getByList(List<T> list) throws IllegalAccessException {
        try {
            // 创建Excel文档
            HSSFWorkbook hwb = new HSSFWorkbook();
            for(int k = 0; k < list.size(); k=k+50000 ){
                HSSFSheet sheet = hwb.createSheet();
                for (int i = k, j = 0; i < list.size() && i < k+50000; i++, j++){
                    T t = list.get(i);
                    HSSFRow row = sheet.createRow(j);
                    Class cls = t.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for(int h = 0; h < fields.length; h++){
                        HSSFCell xh = row.createCell(h);
                        if( t.getClass() == String.class ){
                            xh.setCellValue(t.toString());
                            break;
                        }
                        Field f = fields[j];
                        f.setAccessible(true);
                        if ( f.getType() == Date.class) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Object obj = f.get(t);
                            xh.setCellValue(obj != null ? sdf.format(obj) : "");
                        } else {
                            Object obj = f.get(t);
                            xh.setCellValue(obj != null ? String.valueOf(f.get(t)) : "");
                        }
                    }
                }
            }
            return hwb;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * Excel数据保存到文件
     * @param filePath
     * @param hwb
     * @throws IOException
     */
    public static void save(String filePath, HSSFWorkbook hwb) throws IOException {
        OutputStream out = new FileOutputStream(filePath);
        hwb.write(out);
        out.flush();
        out.close();
    }
}
