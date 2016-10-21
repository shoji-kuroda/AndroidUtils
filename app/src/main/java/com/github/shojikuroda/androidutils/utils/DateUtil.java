package com.github.shojikuroda.androidutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shoji.kuroda on 2016/10/21.
 */

public class DateUtil {

    public static Date strToDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.JAPAN);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date strToDate(String str, String[] formatList) {
        for (String format : formatList) {
            Date date = strToDate(str, format);
            if (date != null) {
                return date;
            }
        }
        return null;
    }
}
