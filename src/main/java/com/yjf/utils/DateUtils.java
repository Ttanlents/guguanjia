package com.yjf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 余俊锋
 * @date 2020/10/22 19:41
 * @Description
 */
public class DateUtils {
    public static String parseDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
      return   dateFormat.format(date);
    }

    public static Date parse(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            return   dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
