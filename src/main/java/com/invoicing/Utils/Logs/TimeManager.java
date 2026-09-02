package com.invoicing.Utils.Logs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {

    public static String getSimpleTimeStamp() {
        /*
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

         */
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getUniqueSimpleTimeStamp() {

        return new SimpleDateFormat("ss").format(new Date());
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }


}
