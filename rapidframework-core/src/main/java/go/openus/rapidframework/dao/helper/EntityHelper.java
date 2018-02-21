package go.openus.rapidframework.dao.helper;

import go.openus.rapidframework.dao.annotation.Column;
import go.openus.rapidframework.dao.annotation.NotColumn;
import go.openus.rapidframework.dao.annotation.Table;
import go.openus.rapidframework.utils.MapUtils;
import go.openus.rapidframework.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体类处理
 */
public class EntityHelper {
    /**
     * 实体类 => 表名
     */
    private static final Map<String, String> entityClassTableNameMap = new ConcurrentHashMap<String, String>();

    /**
     * 实体类 => 主键字段名
     */
    private static final Map<String, String> entityClassPkColumnNameMap = new ConcurrentHashMap<String, String>();

    /**
     * 实体类 => (字段名 => 列名)
     */
    private static final Map<String, Map<String, String>> entityClassFieldMap = new ConcurrentHashMap<String, Map<String, String>>();

    /**
     * 获取实体类对应的表名称
     * @param entityClass 实体类表名称
     * @return 表名称
     */
    public static String getTableName(Class<?> entityClass) {
        String tableName = entityClassTableNameMap.get(entityClass.getName());
        if(StringUtils.isEmpty(tableName)){
            tableName = initEntityNameMap(entityClass);
        }
        return tableName;
    }

    /**
     * 获取实体类对应的表的主键字段名称
     * @param entityClass 实体类表名称
     * @return 表名称
     */
    public static String getPkColumnName(Class<?> entityClass) {
        String pkColumnName = entityClassPkColumnNameMap.get(entityClass.getName());
        if(StringUtils.isEmpty(pkColumnName)){
            pkColumnName = initPkColumnNameMap(entityClass);
        }
        return pkColumnName;
    }

    /**
     * 获取实体类的列字段（Key）与对象属性（Value）的映射Map
     * @param entityClass 实体类
     * @return 列字段（Key）与对象属性（Value）的映射Map
     */
    public static Map<String, String> getFieldMap(Class<?> entityClass) {
        Map<String, String> fieldMap = entityClassFieldMap.get(entityClass.getName());
        if(fieldMap == null){
            fieldMap = initEntityFieldMap(entityClass);
        }
        return fieldMap;
    }

    /**
     * 获取实体类的变量（Key）与列字段（Value）映射Map
     * @param entityClass 实体类
     * @return 变量（Key）与列字段（Value）的映射Map
     */
    public static Map<String, String> getColumnMap(Class<?> entityClass) {
        return MapUtils.invert(getFieldMap(entityClass));
    }

    /**
     * 根据变量名获取列表名称
     * @param entityClass 实体类
     * @param fieldName 变量名
     * @return 变量对应列字段
     */
    public static String getColumnName(Class<?> entityClass, String fieldName) {
        String columnName = getFieldMap(entityClass).get(fieldName);
        return StringUtils.isNotEmpty(columnName) ? columnName : StringUtils.camelHumpToUnderline(fieldName);
    }

    // ====

    /**
     * 初始化实体类-表名的映射Map
     * @param entityClass
     * @return
     */
    private static String initEntityNameMap(Class<?> entityClass) {
        //判断是否是实体类
        if(!entityClass.isAnnotationPresent(Table.class)){
            throw new RuntimeException(entityClass.getName() + " is not annotate the Entity class");
        }
        // 读取注解中定义的表名
        Table table = entityClass.getAnnotation(Table.class);
        String tableName = table.value();
        if(StringUtils.isEmpty(tableName)){ tableName = table.name(); }
        // 若没有使用Table注解或者注解的表名为空，则使用实体类名转换为下划线风格的表名
        if(StringUtils.isEmpty(tableName)) { tableName = StringUtils.camelHumpToUnderline(entityClass.getSimpleName()); }
        entityClassTableNameMap.put(entityClass.getName(), tableName);
        return tableName;
    }

    /**
     * 初始化实体类-字段名的映射Map
     * @param entityClass
     * @return
     */
    private static  Map<String, String> initEntityFieldMap(Class<?> entityClass) {
        //判断是否是实体类
        if(!entityClass.isAnnotationPresent(Table.class)){
            throw new RuntimeException(entityClass.getName() + " is not annotate the Entity class");
        }
        // 获取并遍历该实体类中所有的字段（不包括父类中的方法）
        Field[] fields = entityClass.getDeclaredFields();
        Map<String, String> fieldMap = null;
        if (ArrayUtils.isNotEmpty(fields)) {
            // 创建一个 fieldMap（用于存放列名与字段名的映射关系）
            fieldMap = new HashMap<String, String>();
            for (Field field : fields) {
                String fieldName = field.getName();
                String columnName = null;

                // 如果是关联列，则表明数据表中没有对应的列
                if (field.isAnnotationPresent(NotColumn.class)) { continue; }

                // 判断该字段上是否存在 Column 注解
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);

                    // 如果注解中明确忽略了该字段，则表明数据库中没有对应的列
                    columnName = column.value();
                }
                // 若没有使用Column注解或者注解的列名为空，则使用字段名转换为下划线风格的列名
                if(StringUtils.isEmpty(columnName)) {
                    columnName = StringUtils.camelHumpToUnderline(fieldName);
                }
                fieldMap.put(fieldName, columnName);
            }

            if(fieldMap!=null) {
                entityClassFieldMap.put(entityClass.getName(), fieldMap);
            }
        }
        return fieldMap;
    }

    /**
     * 初始化实体类-主键字段名的映射Map
     * @param entityClass
     * @return
     */
    private static String initPkColumnNameMap(Class<?> entityClass) {
        //判断是否是实体类
        if(!entityClass.isAnnotationPresent(Table.class)){
            throw new RuntimeException(entityClass.getName() + " is not annotate the Entity class");
        }
        // 读取该注解中定义的表名
        String pkColumnName = entityClass.getAnnotation(Table.class).pkColumnName();
        if(StringUtils.isNotEmpty(pkColumnName)) { entityClassPkColumnNameMap.put(entityClass.getName(), pkColumnName); }
        return pkColumnName;
    }

}
