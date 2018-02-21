package go.openus.common.utils;

public class StringUtils {

	/**
	 * 去掉字符串的回车符合换行符
	 * @param value
	 * @return
	 */
    public static String rmoveLineBreaks(String value) {
    	return value.replaceAll("\r|\n", "");
    }

}
