package go.openus.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 */
public class DateUtils {

	//日期时间默认格式
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd H:m:s";
	//日期默认格式
	private static final String DATE_FORMAT = "yyyy-MM-dd";


	/**
	 * <b>获取当前日期</b><br>
	 * y 年 M 月 d 日 H
	 * @return 日期
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(new Date());
	}


	/**
	 * <b>获取当前时间</b><br>
	 * y 年 M 月 d 日 H 24小时制 h 12小时制 m 分 s 秒
	 * @return 日期 + 时间
	 */
	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		return sdf.format(new Date());
	}

	/**
	 * <b>获取当前时间</b><br>
	 * y 年 M 月 d 日 H 24小时制 h 12小时制 m 分 s 秒
	 * @param format 日期格式
	 * @return 日期 + 时间 | 日期
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取制定日期的格式化字符串
	 * @param date Date 日期
	 * @param format String 格式
	 * @return String
	 */
	public static String getFormatedDate(Date date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 判断哪个日期在前 日过日期一在日期二之前，返回true,否则返回false
	 * @param date1 日期一
	 * @param date2 日期二
	 * @return boolean
	 */
	public static boolean isBefore(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		
		return c1.before(c2);
	}
	
	/**
	 * 将字符串转换成日期
	 * @param date String 日期字符串
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.parse(date);
	}

	/**
	 * 将字符串转换成日期-时间
	 * @param date String 日期字符串
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDateTime(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		return sdf.parse(date);
	}

	/**
	 * 将字符串转换成日期
	 * @param date String 日期字符串
	 * @param format String 日期格式字符串
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String date,String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	
//	/**
//	 * 获取指定月份的最后一天，
//	 * @param date Date类型
//	 * @param date1 String类型 yyyy-MM-dd mm:HH:ss 或 yyyy-MM-dd
//	 * @return Date
//	 * @throws ParseException
//	 */
//	public static Date lastDayOfMonth(Date date,String date1) throws ParseException{
//		Date _date=null;
//		if(null!=date){
//			_date = date;
//		}
//		if(null==date && null!=date1){
//			_date = parseDate(date1);
//		}
//
//		Calendar cal=Calendar.getInstance();
//		assert _date != null;
//		cal.setTime(_date);
//		cal.add(Calendar.MONTH, 1);
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		cal.add(Calendar.DAY_OF_MONTH, -1);
//		return cal.getTime();
//	}
	
	/**
	 * 是否是闰年
	 * @param year 年份
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
	       GregorianCalendar calendar = new GregorianCalendar();
	       return calendar.isLeapYear(year);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param date1 较小的时间
	 * @param date2  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date date1,Date date2) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
		date1 = sdf.parse(sdf.format(date1));
		date2 = sdf.parse(sdf.format(date2));

		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();

		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();

		//1000 * 3600 * 24 = 86400000
		long between_days=(time2 - time1)/(86400000);

		return Integer.parseInt(String.valueOf(between_days));
	}


}
