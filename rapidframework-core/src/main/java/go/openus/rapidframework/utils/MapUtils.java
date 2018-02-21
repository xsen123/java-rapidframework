package go.openus.rapidframework.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 映射操作工具类
 *
 */
public class MapUtils {

    /**
     * 判断 Map 是否非空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return org.apache.commons.collections.MapUtils.isNotEmpty(map);
    }

    /**
     * 判断 Map 是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return org.apache.commons.collections.MapUtils.isEmpty(map);
    }

    /**
     * 将Map的键与值进行转置
     * @param source
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<V, K> invert(Map<K, V> source) {
        Map<V, K> target = null;
        if (isNotEmpty(source)) {
            target = new LinkedHashMap<V, K>(source.size());
            for (Map.Entry<K, V> entry : source.entrySet()) {
                target.put(entry.getValue(), entry.getKey());
            }
        }
        return target;
    }

}
