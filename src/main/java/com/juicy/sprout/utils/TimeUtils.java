package com.juicy.sprout.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class TimeUtils {

    public static long getServiceUptime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();

        return rb.getUptime();
    }

    public static String toTimeStamp(long millisecs) {
        return new Timestamp(millisecs).toString();
    }

    public static String printServiceUpTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("DD:HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(getServiceUptime());


        return formatter.format(date);
    }

//    public static String runtime
}
