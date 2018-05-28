package com.leo.model;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class ExcelModel {
    @Getter
    private List<SheetModel> smList;

    public ExcelModel(Workbook wb){
        smList = new ArrayList<SheetModel>();
        int sheetSize = wb.getNumberOfSheets();
        for (int index = 0; index < sheetSize; index++) {
            SheetModel sm = new SheetModel();
            Sheet sheet = wb.getSheetAt(index);
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                LineModel lm = new LineModel();
//                for (int j = 0; j < sheet.)
                sm.push(lm);
            }
            smList.add(sm);

        }
    }

    private class SheetModel{
        @Getter
        private List<LineModel> lines;

        public SheetModel(){
            lines = new ArrayList<LineModel>();
        }

        public int push(LineModel lm){
            lines.add(lm);
            return lines.size();
        }
    }

    private class LineModel{
        @Getter
        private List<String> columns;

        public LineModel(){
            columns = new ArrayList<String>();
        }

        public int push(String content){
            columns.add(content);
            return columns.size();
        }
    }
}
