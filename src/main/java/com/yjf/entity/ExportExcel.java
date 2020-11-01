package com.yjf.entity;

import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public  class ExportExcel<T> {
    public void exportExcel(String[] headers,String[] headersName,
                            Collection<T> dataset, String fileName, HttpServletResponse response) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(fileName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);  //选中首行row  索引为0
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);  //选中当前行的第一个空格
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();   //例如t  代表一个实体User
            Class tClass = t.getClass();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int i = 0; i < headersName.length; i++) {   //遍历实体需要写入Excell的属性
                Field field = null;
                try {
                    field = tClass.getDeclaredField(headersName[i]);
                    field.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                Object value = null;
                try {
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                XSSFCell cell = row.createCell(i);
                String textValue = null;
                // 其它数据类型都当作字符串简单处理
                if (value instanceof Date){
                    String time = dateFormat.format(value);
                    XSSFRichTextString richString = new XSSFRichTextString(time);
                    cell.setCellValue(richString);
                }else {
                    if (value != null && value != "") {
                        textValue = value.toString();
                    }
                    if (textValue != null) {
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);
                        cell.setCellValue(richString);

                    }
                }
            }
        }
        try {
            getExportedFile(workbook, fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //产生输出
    public void getExportedFile(XSSFWorkbook workbook, String name,HttpServletResponse response) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            fos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
