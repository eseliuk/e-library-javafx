package com.stormnet.net.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static Date dateFromString(String dateStr) {
      return dateFromString(dateStr,DEFAULT_DATE_FORMAT);
    }

    public static Date dateFromString(String dateStr, String dateFormatStr) {
        Date date = null;

        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        try {
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static String stringFromDate(Date date) {
        return stringFromDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String stringFromDate(Date date, String dateFormatStr) {
        String  dateStr = null;

        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        try {
            dateStr = dateFormat.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dateStr;
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
