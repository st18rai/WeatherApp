package com.st18apps.weatherapp.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtil {
    public static String makeImageUrl(String weatherIcon) {
        return "http://openweathermap.org/img/wn/" + weatherIcon + "@2x.png";
    }

    public static String convertDate(String date) {

        Date tempDate = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            tempDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateTime dt = new DateTime(tempDate);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMMM, HH:mm");

        return fmt.print(dt);
    }
}
