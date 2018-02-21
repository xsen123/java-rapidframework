package go.openus.common.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 */
public class UUIDUtils {

	/**
	 * 生成36位的UUID
	 * @return 36位UUID
	 */
	public static String generate36()
	{
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成32位的UUID(去掉了中间的连接符)
	 * @return  32位UUID
	 */
	public static String generate32()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
