package go.openus.rapidframework.dao.helper;

import go.openus.rapidframework.utils.MapUtils;
import go.openus.rapidframework.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * SQL语句操作工具类
 *
 */
public class SqlHelper {

    /**
     * 生成 insert 语句
     * @param entityClass
     * @param fieldNames
     * @return
     */
    public static String generateInsertSql(Class<?> entityClass, Collection<String> fieldNames) {
        StringBuilder sql = new StringBuilder("insert into ").append(EntityHelper.getTableName(entityClass));
        if (CollectionUtils.isNotEmpty(fieldNames)) {
            int i = 0;
            StringBuilder columns = new StringBuilder(" ");
            StringBuilder values = new StringBuilder(" values ");
            for (String fieldName : fieldNames) {
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0) {
                    columns.append("(").append(columnName);
                    values.append("(?");
                } else {
                    columns.append(", ").append(columnName);
                    values.append(", ?");
                }
                if (i == fieldNames.size() - 1) {
                    columns.append(")");
                    values.append(")");
                }
                i++;
            }
            sql.append(columns).append(values);
        }
        return sql.toString();
    }

    /**
     * 生成 update 语句
     * @param entityClass
     * @param fields
     * @param condition
     * @return
     */
    public static String generateUpdateColumnSql(Class<?> entityClass, String[] fields, String condition) {
        StringBuilder columnsSql = new StringBuilder();
        for (String field:fields) {
            String column = EntityHelper.getColumnName(entityClass, field);
            columnsSql.append(column+"=?,");
        }
        columnsSql.delete(columnsSql.length()-1, columnsSql.length());

        StringBuilder sql = new StringBuilder("update ")
               .append(EntityHelper.getTableName(entityClass))
                .append(" set ")
                .append(columnsSql.toString())
                .append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 update 语句
     * 适配处理写“字段=?”、只写字段、直接为字段赋值等情况
     *   示例："a,b,c" 、 "a=?,b=?,c=?" 、 "a,b,c='hello',v=v+1" 、 "a=?,b=?,c='hello',v=v+1"
     * @param entityClass
     * @param columnsUpdateSql
     * @param condition
     * @return
     */
    public static String generateUpdateSql(Class<?> entityClass, String columnsUpdateSql, String condition) {
        String realColumnsUpdateSql = columnsUpdateSql;
        if(realColumnsUpdateSql.contains("?")==false){
            String[] columns = realColumnsUpdateSql.split(",");
            for (int index=0; index<columns.length; index++) {
                if(columns[index].contains("=")==false){
                    String realColumn = EntityHelper.getColumnName(entityClass, columns[index]);
                    columns[index] = realColumn.trim() + "=?";
                }
            }
            realColumnsUpdateSql = org.apache.commons.lang3.StringUtils.join(columns, ",");
        }

        StringBuilder sql = new StringBuilder("update ")
                .append(EntityHelper.getTableName(entityClass))
                .append(" set ")
                .append(realColumnsUpdateSql)
                .append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 update 语句
     * @param entityClass
     * @param fieldMap
     * @param condition
     * @return
     */
    public static String generateUpdateSql(Class<?> entityClass, Map<String, Object> fieldMap, String condition) {
        StringBuilder sql = new StringBuilder("update ").append(EntityHelper.getTableName(entityClass));
        if (MapUtils.isNotEmpty(fieldMap)) {
            sql.append(" set ");
            int i = 0;
            for (Map.Entry<String, Object> fieldEntry : fieldMap.entrySet()) {
                String fieldName = fieldEntry.getKey();
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0) {
                    sql.append(columnName).append(" = ?");
                } else {
                    sql.append(", ").append(columnName).append(" = ?");
                }
                i++;
            }
        }
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 delete 语句
     * @param entityClass
     * @param condition
     * @return
     */
    public static String generateDeleteSql(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("delete from ").append(EntityHelper.getTableName(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 select count(*) 语句
     * @param entityClass
     * @param condition
     * @return
     */
    public static String generateSelectSqlForCount(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("select count(*) count from ").append(EntityHelper.getTableName(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 select 语句
     * @param entityClass
     * @param condition
     * @param sort
     * @return
     */
    public static String generateSelectSql(Class<?> entityClass, String condition, String sort) {
        return generateSelectSql(entityClass, condition, sort, "");
    }

    /**
     * 生成 select 语句
     * @param entityClass
     * @param condition
     * @param sort
     * @param limit 示例： 1 或者 0,3
     * @return
     */
    public static String generateSelectSql(Class<?> entityClass, String condition, String sort, String limit) {
        return "select * from " +
                EntityHelper.getTableName(entityClass) +
                generateWhere(condition) +
                generateOrder(sort) +
                generateLimit(limit);
    }

    /**
     * 生成 select 分页语句
     * @param entityClass
     * @param condition
     * @param sort
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static String generateSelectSqlForPager(Class<?> entityClass, String condition, String sort,Integer pageNumber, Integer pageSize) {
        StringBuilder sql = new StringBuilder();
        String table = EntityHelper.getTableName(entityClass);
        String where = generateWhere(condition);
        String order = generateOrder(sort);

        //TODO 这里理想的方式需要通过判断使用的是哪一种jdbc方言来生成对应sql
        appendSqlForMySql(sql, table, where, order, pageNumber, pageSize);

        return sql.toString();
    }

    /**
     * 生成MySql的分页Sql
     * @param sql
     * @param table
     * @param where
     * @param order
     * @param pageNumber
     * @param pageSize
     */
    private static void appendSqlForMySql(StringBuilder sql, String table, String where, String order, Integer pageNumber, Integer pageSize) {
        //select * from 表名 where 条件 order by 排序 limit 开始位置, 结束位置
        sql.append("select * from ").append(table);
        sql.append(where);
        sql.append(order);
        //提供了分页参数
        if(pageNumber != null && pageSize != null){
            int pageStart = (pageNumber - 1) * pageSize;
            sql.append(" limit ").append(pageStart).append(", ").append(pageSize);
        }
    }

    /**
     * 生成where语句
     * @param condition
     * @return
     */
    private static String generateWhere(String condition) {
        String where = "";
        if (StringUtils.isNotEmpty(condition)) {
            where += " where " + condition;
        }
        return where;
    }

    /**
     * 生成order语句
     * @param sort
     * @return
     */
    private static String generateOrder(String sort) {
        String order = "";
        if (StringUtils.isNotEmpty(sort)) {
            order += " order by " + sort;
        }
        return order;
    }

    /**
     * 生成limit语句
     * @param limit
     * @return
     */
    private static String generateLimit(String limit) {
        return (StringUtils.isEmpty(limit) ? "" : (" limit "+limit));
    }

    /**
     * 生成sum语句
     * @param entityClass
     * @param field
     * @param condition
     * @return
     */
    public static String generateSelectSqlForSum(Class<?> entityClass, String field, String condition) {
        StringBuilder sql = new StringBuilder("select sum(")
                .append(EntityHelper.getColumnName(entityClass, field))
                .append(") from ")
                .append(EntityHelper.getTableName(entityClass))
                .append(generateWhere(condition));
        return sql.toString();
    }
}
