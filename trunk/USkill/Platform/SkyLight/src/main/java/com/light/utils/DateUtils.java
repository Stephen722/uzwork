package com.light.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类。
 * 提供一下几种常用日期格式：
 * yyyy-MM-dd，yyyy/MM/dd，yyyy-MM-dd HH:mm，yyyy/MM/dd HH:mm，yyyy-MM-dd HH:mm:ss和yyyy/MM/dd HH:mm:ss。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class DateUtils {

    public static final int MILLIS = 0;
    public static final int SECOND = 1;
    public static final int MINUTE = 2;
    public static final int HOUR = 3;
    public static final int DAY = 4;
    public static final int WEEK = 5;
    public static final long MILLIS_PER_SECOND = 1000;
    public static final long MILLIS_PER_MINUTE = 60000;
    public static final long MILLIS_PER_HOUR = 3600000;
    public static final long MILLIS_PER_DAY = 86400000;
    public static final long MILLIS_PER_WEEK = 604800000;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_TWO = "yyyy/MM/dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_MID_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String TIME_FORMAT_TWO = "yyyy/MM/dd HH:mm:ss";
	public static final String TIME_MID_FORMAT_TWO = "yyyy/MM/dd HH:mm";
	
	/**
	 * 取得当前时间。
	 * 
	 * @return 当前日期（Date）
	 */
	public static Date today() {
		return new Date();
	}
	
	public static Date getFormattedDate(Date date) {
		return getFormattedDate(date, DATE_FORMAT);
	}

	public static Date getFormattedDate(Date date, String format) {
		String todayString = DateUtils.formatDate(date, format);
		Date formattedDate = parseDate(todayString, format);
		return formattedDate;
	}
	
	/**
	 * 取得未来此刻的时间。
	 * 
	 * @return 未来日期（Date）
	 */
	public static Date getNextDay(int i) {
		return new Date(getCurrentTimeMillis() + 0x5265c00L*i);
	}

	/**
	 * 取得去过某天的此刻时间。
	 * 
	 * @param i 过去天数
	 * @return 过去日期（Date）
	 */
	public static Date getLastDay(int i) {
		return new Date(getCurrentTimeMillis() - 0x5265c00L * i);
	}

	/**
	 * 取得当前时间的长整型表示。
	 * 
	 * @return 当前时间（long）
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 用默认日期格式解析日期。
	 * 
	 * @param date
	 * @return 日期
	 */
	public static Date parseDate(String date){
		return parseDate(date, DATE_FORMAT);
	}
	
	/**
	 * 用默认时间格式解析日期时间。
	 * 
	 * @param date
	 * @return 日期
	 */
	public static Date parseTime(String date){
		return parseDate(date, TIME_FORMAT);
	}
	
	/**
	 * 用指定格式解析日期。
	 * 
	 * @param date
	 * @return 日期
	 */
	public static Date parseDate(String date, String format){
		SimpleDateFormat simpleFormat = new SimpleDateFormat();
		simpleFormat.applyPattern(format);
		return simpleFormat.parse(date, new ParsePosition(0));
	}
	
	/**
	 * 使用默认的日期格式格式化日期。
	 * 
	 * @param date 给定日期
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date){
		return formatDate(date, DATE_FORMAT);
	}
	
	/**
	 * 使用默认的日期格式格式化日期。
	 * 
	 * @param date 给定日期
	 * @return 格式化后的字符串
	 */
	public static String formatTime(Date date){
		return formatDate(date, TIME_FORMAT);
	}
	
	/**
	 * 使用给定的日期格式来格式化日期。
	 * 
	 * @param date 给定日期
	 * @param format 给定的日期格式
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date, String format){
		SimpleDateFormat simpleFormat = new SimpleDateFormat();
		simpleFormat.applyPattern(format);
		return simpleFormat.format(date);
	}
	
	/**
	 * 将长整型表示的时间以默认的时间格式返回。
	 * 
	 * @param l 表示某日期的长整型数据
	 * @return 日期型的字符串
	 */
	public static String getDate(long l) {
		return getDate(l, TIME_FORMAT);
	}
	
	/**
	 * 将长整型表示的时间以给定的时间格式返回。
	 * 
	 * @param l 表示某日期的长整型数据
	 * @param format
	 * @return 时间
	 */
	public static String getDate(long l, String format) {
		return formatDate(new Date(l), format);
	}
	
	/**
	 * 返回两个时间相隔的天数。
	 * 
	 * @param date1 
	 * @param date2
	 * @return 间隔天数
	 */
	public static long diffDate(String date1, String date2){
		return diffDate(date1, date2, DAY);
	}

	/**
	 * 返回两个时间相隔的微妙，秒，分，小时，天，周。
	 * 
	 * @param date1 
	 * @param date2
	 * @param diffType 0：微妙，1：秒，2：分，3：小时，4：天，5：周
	 * @return long 间隔数
	 */
	public static long diffDate(String date1, String date2, int diffType){
		Date dt1 = parseTime(date1);
		Date dt2 = parseTime(date2);
		return diffDate(dt1, dt2, diffType);
	}
	
	/**
	 * 返回两个时间相隔的天数。
	 * 
	 * @param date1 
	 * @param date2
	 * @return 间隔天数
	 */
	public static long diffDate(Date date1, Date date2){
		return diffDate(date1, date2, DAY);
	}
	
	/**
	 * 返回两个时间相隔的微妙，秒，分，小时，天，周。
	 * 
	 * @param date1 
	 * @param date2
	 * @param diffType 0：微妙，1：秒，2：分，3：小时，4：天，5：周
	 * @return long 间隔数
	 */
	public static long diffDate(Date date1, Date date2, int diffType){
		long rtnValue = 0l;
		Calendar cd1 = getCalendar(date1);
		Calendar cd2 = getCalendar(date2);
		long millis = cd1.getTimeInMillis() - cd2.getTimeInMillis();
		
		switch (diffType) {
			case MILLIS:
				rtnValue = millis;
				break;
			case SECOND:
				rtnValue = millis / MILLIS_PER_SECOND;
				break;
			case MINUTE:
				rtnValue = millis / MILLIS_PER_MINUTE;
				break;
			case HOUR:
				rtnValue = millis / MILLIS_PER_HOUR;
				break;
			case DAY:
				rtnValue = millis / MILLIS_PER_DAY;
				break;
			case WEEK:
				rtnValue = millis / MILLIS_PER_WEEK;
				break;
			default:
				rtnValue = millis / MILLIS_PER_DAY;
				break;
		}
		return rtnValue;
	}

    /**
     * 取得当期日期的年份。
     *
     * @return int 年份
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    /**
     * 取得给定日期的年份。
     *
     * @param date
     * @return 年份
     */
    public static int getYear(Date date) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 取得当期日期的月份。
     *
     * @return int 月份
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    
    /**
     * 取得给定日期的月份。
     *
     * @param date
     * @return 月份
     */
    public static int getMonth(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得当前日期当月的第几周。
     *
     * @return int 第几周
     */
    public static int getWeekOfMonth() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
    }
    
    /**
     * 取得给定日期当月的第几周。
     *
     * @param date
     * @return 第几周
     */
    public static int getWeekOfMonth(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 取得当前日期的在当月的第几天。
     *
     * @return 第几天
     */
    public static int getDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 取得给定日期的在当月的第几天。
     *
     * @return int 第几天
     */
    public static int getDayOfMonth(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得给定日期的在当周的第几天。
     *
     * @param date
     * @return int 第几天 1表示星期天， 7表示星期六
     */
    public static int getDayOfWeek(Date date) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
	/**
	 * 取得给定日期的小时数，12小时制。
	 * 
	 * @return int 小时数
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR);
	}
	
	/**
	 * 取得给定日期的小时数，24小时制。
	 * 
	 * @return 小时数
	 */
	public static int getHourOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取得给定日期的分钟数。
	 * 
	 * @return 分钟数
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 取得给定日期的秒数。
	 * 
	 * @return 秒数
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	
    /**
     * 创建一个日期。
     *
     * @param year  真正的年份，不用减去1900
     * @param month 真正的月份，不用减去1
     * @param day
     * @return 日期
     */
    public static Date makeDate(int year, int month, int day) {
    	Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
    
	/**
	 * 取得Calendar
	 * @param l 长整型时间
	 * @return Calendar
	 */
	public static Calendar getCalendar(long l) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(l);
		return calendar;
	}

	/**
	 * 取得Calendar
	 * @param date 日期
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		return calendar;
	}
	
//	public static void main(String[] args){
//		System.out.println(DateUtils.getDayOfMonth(today()));
//		System.out.println(DateUtils.getDayOfWeek(today()));
//		System.out.println(DateUtils.getYear());
//		System.out.println(DateUtils.getMonth());
//		System.out.println(DateUtils.getMinute(today()));
//		System.out.println(DateUtils.getHour(today()));
//		System.out.println(DateUtils.getHourOfDay(today()));
//		System.out.println(DateUtils.getSecond(today()));
//		
//		Date d2 = DateUtils.parseDate("2015-12-23");
//		System.out.println(d2.after(DateUtils.today()));
//		
//	}
}