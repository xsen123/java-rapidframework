package go.openus.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formater {
	protected static final DecimalFormat RATE_FORMAT = new DecimalFormat(
			"#0.00");

	protected static final DecimalFormat RATE_PERCENT_FORMAT = new DecimalFormat(
			"#0.00%");

	protected static final DecimalFormat PROESS_FORMAT = new DecimalFormat(
			"#0%");

	protected static final DecimalFormat AMOUNT_SPLIT_FORMAT = new DecimalFormat(
			"#,##,##0.00");

	protected static final DecimalFormat AMOUNT2_SPLIT_FORMAT = new DecimalFormat(
			"#,##,##0");

	protected static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat(
			"#####0.00");

	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	protected static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm:ss");

	protected static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	protected static final SimpleDateFormat DATETIME_FORMAT2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	protected static final SimpleDateFormat DATETIME_FORMAT3 = new SimpleDateFormat(
			"yyyy年MM月dd日");
	
	protected static final SimpleDateFormat DATETIME_FORMAT_2 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public static String datetimeFormat2(Date date) {
		if (date == null) {
			return "";
		}
		return DATETIME_FORMAT_2.format(date);
	}
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMAT.format(date);
	}

	public static String formatTime(Date date) {
		if (date == null) {
			return "";
		}
		return TIME_FORMAT.format(date);
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return DATETIME_FORMAT.format(date);
	}

	public static String formatDateTime2(Date date) {
		if (date == null) {
			return "";
		}
		return DATETIME_FORMAT2.format(date);
	}

	public static String formatChinaDate(Date date){
		if (date == null) {
			return "";
		}
		return DATETIME_FORMAT3.format(date);
	}
	
	public static String formatRate(float number) {
		return RATE_PERCENT_FORMAT.format(number);
	}

	public static String formatRate(double number) {
		return RATE_PERCENT_FORMAT.format(number);
	}

	public static String formatRate(BigDecimal number) {
		return RATE_PERCENT_FORMAT.format(number);
	}

	public static String formatRate(float number, boolean includePercent) {
		return includePercent ? RATE_PERCENT_FORMAT.format(number)
				: RATE_FORMAT.format(number * 100.0F);
	}

	public static String formatRate(double number, boolean includePercent) {
		return includePercent ? RATE_PERCENT_FORMAT.format(number)
				: RATE_FORMAT.format(number * 100.0D);
	}

	public static String formatRate(BigDecimal number, boolean includePercent) {
		return includePercent ? RATE_PERCENT_FORMAT.format(number)
				: RATE_FORMAT.format(number.multiply(new BigDecimal(100)));
	}
	
	/**
	 * 根据字符串生成对应的Date对象
	 * @param strDate
	 * @return
	 */
	public static Date parseDate2(String strDate)
	{
		try
		{
			return DATETIME_FORMAT_2.parse(strDate);
		}
		catch(ParseException e)
		{
			return null;
		}
	}

	public static int roundProgress(double number) {
		double v = number * 100.0D;
		if (v <= 0.0D) {
			return 0;
		}
		if (v <= 1.0D) {
			return 1;
		}
		return (int) (Math.floor(number * 100.0D) / 100.0D);
	}

	public static int roundProgress(float number) {
		double v = number * 100.0F;
		if (v <= 0.0D) {
			return 0;
		}
		if (v <= 1.0D) {
			return 1;
		}
		return (int) (Math.floor(number * 100.0F) / 100.0D);
	}

	public static String formatProgress(double number) {
		double v = number * 100.0D;
		if (v <= 0.0D) {
			return "0%";
		}
		if (v <= 1.0D) {
			return "1%";
		}
		return PROESS_FORMAT.format(Math.floor(number * 100.0D) / 100.0D);
	}

	public static String formatProgress(float number) {
		double v = number * 100.0F;
		if (v <= 0.0D) {
			return "0%";
		}
		if (v <= 1.0D) {
			return "1%";
		}
		return PROESS_FORMAT.format(Math.floor(number * 100.0F) / 100.0D);
	}

	public static String formatAmount(int number) {
		return AMOUNT_SPLIT_FORMAT.format(number);
	}

	public static String formatAmount(long number) {
		return AMOUNT_SPLIT_FORMAT.format(number);
	}

	public static String formatAmount(float number) {
		return AMOUNT_SPLIT_FORMAT.format(number);
	}

	public static String formatAmount(double number) {
		return AMOUNT_SPLIT_FORMAT.format(number);
	}

	public static String formatAmount(BigDecimal number) {
		if (number == null) {
			return "";
		}
		return AMOUNT_SPLIT_FORMAT.format(number);
	}

	public static String formatAmount(BigInteger number) {
		if (number == null) {
			return "";
		}
		return AMOUNT_SPLIT_FORMAT.format(number);
	}

	public static String formatAmount(int number, boolean split) {
		return split ? AMOUNT_SPLIT_FORMAT.format(number) : AMOUNT_FORMAT
				.format(number);
	}

	public static String formatAmount(long number, boolean split) {
		return split ? AMOUNT_SPLIT_FORMAT.format(number) : AMOUNT_FORMAT
				.format(number);
	}

	public static String formatAmount(float number, boolean split) {
		return split ? AMOUNT_SPLIT_FORMAT.format(number) : AMOUNT_FORMAT
				.format(number);
	}

	public static String formatAmount(double number, boolean split) {
		return split ? AMOUNT_SPLIT_FORMAT.format(number) : AMOUNT_FORMAT
				.format(number);
	}

	public static String formatAmount(BigDecimal number, boolean split) {
		if (number == null) {
			return "";
		}
		return split ? AMOUNT_SPLIT_FORMAT.format(number) : AMOUNT_FORMAT
				.format(number);
	}

	public static String formatAmount(BigInteger number, boolean split) {
		if (number == null) {
			return "";
		}
		return split ? AMOUNT_SPLIT_FORMAT.format(number) : AMOUNT_FORMAT
				.format(number);
	}

	/**
	 * 格式化金额为元/万元/亿元
	 * 
	 * @param number
	 *            金额
	 * @param isShow
	 *            是否显示汉字
	 * @return
	 */
	public static String formatAmountWan(BigDecimal number, boolean isShow) {
		if (number == null) {
			return "";
		}
		if (number.doubleValue() > 100000000) {
			if(isShow){
				return String.valueOf(number.doubleValue() / 100000000)
						+  "亿元";
			}else{
				return String.valueOf(number.doubleValue() / 100000000) ;
			}
		} else if (number.doubleValue() >= 10000) {
			if(isShow){
				return String.valueOf(number.doubleValue() / 10000)
						+  "万" ;
			}else{
				return String.valueOf(number.doubleValue() / 10000);
			}
		} else {
			if(isShow){
				return AMOUNT2_SPLIT_FORMAT.format(number) +  "元";
			}else{
				return AMOUNT2_SPLIT_FORMAT.format(number);
			}
		}
	}

	/**
	 * 格式化投资期限为天/个月
	 * 
	 * @param dayOrMonth
	 *            投资期限
	 * @param isDay
	 *            是否是天
	 * @param isShow
	 *            是否显示汉字
	 * @return
	 */
	public static String formateInvestDate(Integer dayOrMonth, boolean isDay,
			boolean isShow) {
		if (dayOrMonth == null || dayOrMonth == 0) {
			return "";
		}
		if (isShow) {
			if (isDay) {
				return "<b>"+dayOrMonth+"</b>" + "<span></span>";
			} else {
				return "<b>"+dayOrMonth+"</b>" + "<span></span>";
			}
		}
		return String.valueOf(dayOrMonth);
	}

	/**
	 * 得到满标用时
	 * 
	 * @param INST_END_TIME
	 *            满标时间
	 * @param INST_START_TIME
	 *            发布时间
	 * @return
	 */
	public static String getmbys(Date INST_END_TIME, Date INST_START_TIME) {
		if (INST_END_TIME.before(INST_START_TIME))
			return "";
		long hm = 1000 * 3600 * 24;
		long time = INST_END_TIME.getTime() - INST_START_TIME.getTime();
		long day = time / hm;
		long hour = (time - day * hm) / (1000 * 3600);
		long min = (time - day * hm - hour * 1000 * 3600) / (1000 * 60);
		long ss = (time - day * hm - hour * 1000 * 3600 - min * 1000 * 60) / (1000);
		return day + "天" + hour + "时" + min + "分" + ss + "秒";
	}

	public static void main(String[] args) {
		String dd = formateInvestDate(1000, false, false);
		System.out.println(dd);
	}

	/**
	 *  *替换
	 * @param str 处理之前的字符串  str="123456"
	 * @param start 前面保留字符串位数 start=1
	 * @param end 后面保留字符串位数 end=2
	 * @return  替换中间字符的字符串 1***56
	 * @throws Exception
	 */
	public static String replaceStr(String str, int start, int end)
			throws Exception {
		// *的个数
		int starNumber = str.length() - start - end;
		if (starNumber > 0) {
			String startStr = str.substring(0, start);
			String endStr = str.substring(str.length() - end);
			StringBuffer sb = new StringBuffer();
			sb.append(startStr);
			for (int i = 0; i < starNumber; i++) {
				sb.append("*");
			}
			sb.append(endStr);
			str = sb.toString();
		}
		return str;
	}
	
	/**
	 * 格式化投资流水号 长度不够中间补0
	 * @param prefixStr 需求拼接的前缀 
	 * @param postfixStr 需要拼接的后缀 
	 * @param oldStr 原始字符串 
	 * @param size 返回的字符串长度  
	 * @return 
	 * Example: 
	 * 		prefixStr = “01”
	 * 		postfixStr =“11”
	 * 		oldStr = “123”
	 * 		size = 8
	 * 		return "01012311"
	 */
	public static String formatString(String prefixStr,String postfixStr, String oldStr,int size){
		StringBuffer sb = new StringBuffer();
		int temp = 0;
		if(prefixStr !=null && prefixStr.length() > 0 ){
			sb.append(prefixStr);
			temp = temp + prefixStr.length();
		}
		if(postfixStr != null && postfixStr.length() > 0 ){
			temp = temp + postfixStr.length();
		}
		if(oldStr != null && oldStr.length() > 0 ){
			temp = temp + oldStr.length();
		}
		if(temp < size){
			for(int i=0;i< size - temp;i++){
				sb.append("0");
			}
			sb.append(oldStr);
		}
		if(postfixStr != null && postfixStr.length() > 0 ){
			sb.append(postfixStr);
		}
		return sb.toString();
	}
}