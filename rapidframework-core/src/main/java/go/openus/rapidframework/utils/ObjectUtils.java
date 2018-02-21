package go.openus.rapidframework.utils;

import go.openus.rapidframework.dao.annotation.NotColumn;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 对象操作工具类
 */
public class ObjectUtils {
    /**
     * 获取成员变量的值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object propertyValue = null;
        try {
            if (PropertyUtils.isReadable(obj, fieldName)) {
                propertyValue = PropertyUtils.getProperty(obj, fieldName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return propertyValue;
    }

    /**
     * 将对象进行字段与值的映射（字段名 => 字段值），忽略static字段，不忽略值为null的字段
     * @param obj 对象
     * @return 对象转换后的map
     */
    public static Map<String, Object> getFieldValueMap(Object obj) {
        return getFieldValueMap(obj, true);
    }

    /**
     * 将对象进行字段与值的映射（字段名 => 字段值），不忽略值为null的字段
     * @param obj
     * @param isStaticIgnored
     * @return
     */
    public static Map<String, Object> getFieldValueMap(Object obj, boolean isStaticIgnored)
    {
        return   getFieldValueMap(obj,isStaticIgnored,false);
    }

    /**
     * 将对象进行字段与值的映射（字段名 => 字段值）,可指定是否忽略静态变量
     * @param obj 转换对象
     * @param isStaticIgnored 是否忽略静态变量
     * @return 对象转换后的map
     */
    public static Map<String, Object> getFieldValueMap(Object obj, boolean isStaticIgnored, boolean isNullIgnored) {
        Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isStaticIgnored && Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            // 判断该字段上是否存在 NotColumn 注解，如果有则表明数据表中没有对应的列
            if (field.isAnnotationPresent(NotColumn.class)) { continue; }

            String fieldName = field.getName();
            Object fieldValue = getFieldValue(obj, fieldName);

           //  如果值为空则忽略
            if(isNullIgnored && fieldValue==null)
            {
                continue;
            }
             fieldMap.put(fieldName, fieldValue);
        }
        return fieldMap;
    }


    /**
     * 取得类定义的泛型类
     * @param clazz
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public static Class<?> getParameterizedType(Class<?> clazz, int index) throws IndexOutOfBoundsException {
        Type genericType = clazz.getGenericSuperclass();
        if (!(genericType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class<?>)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }
}
