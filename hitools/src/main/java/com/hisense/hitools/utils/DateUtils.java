package com.hisense.hitools.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class DateUtils {
    public static final String[] zodiacArr = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    public static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int yearByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int monthByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int weekByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int dayByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int hourByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int minuteByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int secondByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static int millisecondByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MILLISECOND);
    }

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static String zodicaByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取星座
     *
     * @return
     */
    public static String constellationByDate(@NonNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        return constellationArr[11];
    }


    /***
     *获取于一个日期相差几天的某天的开始时间
     *
     * @param date      参照日期
     * @param offset    偏移日期
     * @return 对应天的开始日期
     */
    public static Date beginTimeOfDayByDate(@NonNull Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, offset);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /***
     *获取于一个日期相差几天的某天的结束时间
     *
     * @param date      参照日期
     * @param offset    偏移日期
     * @return 对应天的结束日期
     */
    public static Date endTimeOfDayByDate(@NonNull Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, offset);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    /***
     *获取于一个日期对应的周的开始时间
     *
     * @param date      参照日期
     * @return 对应周的开始日期，周日为第一天
     */
    public static Date beginTimeOfWeekByDate(@NonNull Date date, int offset) {
        int weakday = weekByDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7 * offset - weakday + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /***
     *获取于一个日期对应的周的结束时间
     *
     * @param date      参照日期
     * @return 对应周的结束日期
     */
    public static Date endTimeOfWeekByDate(@NonNull Date date, int offset) {
        int weakday = weekByDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7 * offset + 7 - weakday);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /***
     *获取于一个日期对应的月的开始时间
     *
     * @param date      参照日期
     * @return 对应月的开始日期
     */
    public static Date beginTimeOfMonthByDate(@NonNull Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, offset);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /***
     *获取于一个日期对应的月的结束时间
     *
     * @param date      参照日期
     * @return 对应月的结束日期
     */
    public static Date endTimeOfMonthByDate(@NonNull Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, offset);
        int day = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /***
     *获取于一个日期对应的年的开始时间
     *
     * @param date      参照日期
     * @return 对应年的开始日期
     */
    public static Date beginTimeOfYearByDate(@NonNull Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, offset);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /***
     *获取于一个日期对应的年的结束时间
     *
     * @param date      参照日期
     * @return 对应年的结束日期
     */
    public static Date endTimeOfYearByDate(@NonNull Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, offset);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /***
     * 两个时间间的间隔
     * @param date1  第一个时间
     * @param date2  第二个时间
     * @return 两个时间间的间隔(毫秒)
     */
    public static long intervalBetweenDates(@NonNull Date date1, @NonNull Date date2) {
        return date1.getTime() - date2.getTime();
    }

    /**
     * 获取两个日期中的最大日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date maximumBetweenDates(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    /**
     * 获取两个日期中的最小日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date minimumBetweenDates(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    /**
     * 两个日期相差的天数(隔着一个24点就算是一天)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceBetweenDates(@NonNull Date date1, @NonNull Date date2) {
        Date max = maximumBetweenDates(date1, date2);
        Date min = minimumBetweenDates(date1, date2);
        Date end = endTimeOfDayByDate(max, 0);
        Date start = beginTimeOfDayByDate(min, 0);
        long milliseconds = intervalBetweenDates(end, start);
        long millisecondsday = 24 * 60 * 60 * 1000;
        long days = milliseconds / millisecondsday;
        return days;
    }

    /**
     * 格式化日期
     * yyyy-MM-dd HH:mm:ss
     *
     * @param @param  date
     * @param @return
     * @Description:
     */
    public static Date format(String sdate, String pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sd.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化日期
     * yyyy-MM-dd HH:mm:ss
     *
     * @param @param  date
     * @param @return
     * @Description:
     */
    public static String format(@NonNull Date date, String pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        String result = null;
        try {
            result = sd.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String format(long time, String pattern) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(d);
    }
}
