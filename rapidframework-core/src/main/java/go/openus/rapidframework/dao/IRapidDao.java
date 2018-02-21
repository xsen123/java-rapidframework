package go.openus.rapidframework.dao;

import go.openus.rapidframework.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 快速开发Dao接口
 *
 */
public interface IRapidDao extends IBaseDao {

    // ==== 新增记录 START ====
    /**
     * 根据对象插入一条数据
     * @param entity 实体对象
     * @return
     */
    boolean insert(BaseEntity entity);

    /**
     * 根据Map插入一条数据
     * @param entityClass 实体类
     * @param fieldMap 属性-值Map
     * @return
     */
    boolean insert(Class<?> entityClass, Map<String, Object> fieldMap);

    /**
     * 根据对象插入一条数据，返回主键值(该方法仅能用于主键为自增长类型的表)
     * @param entity 实体对象
     * @return
     */
    Integer insertReturnKey(BaseEntity entity);

    /**
     * 据Map插入一条数据，返回主键值(该方法仅能用于主键为自增长类型的表)
     *
     * @param entityClass 实体类
     * @param fieldMap 实体类对应的变量映射Map
     * @return 返回记录ID
     */
    Integer insertReturnKey(Class<?> entityClass, final Map<String, Object> fieldMap);
    // ==== 新增记录 END ====

    // ==== 删除记录 START ====
    /**
     * 根据主键删除对应的记录
     * @param entityClass
     * @param id
     * @return
     */
    boolean delete(Class<?> entityClass, Object id);

    /**
     * 根据条件删除记录
     * @param entityClass 实体类
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 删除结果
     */
    boolean delete(Class<?> entityClass, String condition, Object... params);
    // ==== 删除记录 END ====

    // ==== 更新记录 START ====
    /**
     * 更新数据
     * @param entity 实体对象
     * @return
     */
    boolean update(BaseEntity entity);

    /**
     * 根据条件更新数据
     * @param entity 实体对象
     * @param condition  除了主键外的其他条件，是一个参数化sql
     * @param params sql参数值
     * @return
     */
    boolean update(BaseEntity entity, String condition, Object... params);

    /**
     * 根据条件和字段值Map更新/批量更新数据
     * @param entityClass 实体类
     * @param fieldMap 属性-值Map
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 执行结果
     */
    boolean update(Class<?> entityClass, Map<String, Object> fieldMap, String condition, Object... params);

    /**
     * 根据条件更新部分字段，用来批量更新数据
     * @param entityClass 实体类
     * @param columns 字段更新Sql
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 更新记录数
     */
    int updateCols(Class<?> entityClass, String[] columns, String condition, Object... params);

    /**
     * 根据条件执行更新部分字段的sql，用来更新/批量更新数据
     * 适配处理写“字段=?”、只写字段、直接为字段赋值等情况
     *   示例："a,b,c" 、 "a=?,b=?,c=?" 、 "a,b,c='hello',v=v+1" 、 "a=?,b=?,c='hello',v=v+1"
     * @param entityClass 实体类
     * @param columnsUpdateSql 字段更新Sql
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 更新记录数
     */
    int updateCols(Class<?> entityClass, String columnsUpdateSql, String condition, Object... params);
    // ==== 更新记录 END ====

    // ==== 查询记录 START ====
    /**
     * 根据主键查询带有所有列值的数据对象
     * @param entityClass 实体类
     * @param id 主键名称
     * @return
     */
    <T> T fetch(Class<T> entityClass, Object id);

    /**
     * 根据条件查询带有所有列值的数据对象
     * @param entityClass 实体类
     * @param condition 参数化sql
     * @param params sql参数值
     * @return
     */
    <T> T fetch(Class<T> entityClass, String condition, Object... params);

    /**
     * 根据条件查询带有指定列值的数据对象
     * @param entityClass 实体类
     * @param columnNames 需查询的字段
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 查询结果对象
     */
    <T> T fetchCols(Class<T> entityClass, String columnNames, String condition, Object... params);

    /**
     * 根据条件查询带有所有列值的数据Map
     * @param entityClass 实体类
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 查询结果的映射Map
     */
    Map<String, Object> fetchMap(Class<?> entityClass, String condition, Object... params);

    /**
     * 根据条件查询带有指定列值得数据Map
     * @param entityClass 实体类
     * @param columnNames 查询的列字段
     * @param condition 参数化sql
     * @param params sql参数值
     * @return 查询结果的映射Map
     */
    Map<String, Object> fetchColsMap(Class<?> entityClass, String columnNames,
                                     String condition, Object... params);

    /**
     * 查询全表、带有所有列值的数据对象列表（未排序、未分页）
     * @param entityClass 实体类
     * @return 列表记录
     */
    <T> List<T> query(Class<T> entityClass);

    /**
     * 根据条件查询带有所有列值的数据对象列表（未排序、未分页）
     * @param entityClass 实体类
     * @param condition   参数化sql
     * @param params      sql参数值
     * @return 列表记录
     */
    <T> List<T> query(Class<T> entityClass, String condition, Object... params);

    /**
     * 根据条件查询带有所有列值的、排序后的数据对象列表（有排序，未分页）
     * @param entityClass 实体类
     * @param sort        排序条件
     * @return 列表记录
     */
    <T> List<T> querySorted(Class<T> entityClass, String sort);

    /**
     * 根据条件查询带有所有列值的、排序后的数据对象列表（有排序，未分页）
     * @param entityClass 实体类
     * @param condition   参数化sql
     * @param sort        排序条件
     * @param params      sql参数值
     * @return 列表记录
     */
    <T> List<T> querySorted(Class<T> entityClass, String condition, String sort, Object... params);

    /**
     * 根据条件查询带有所有列值的、排序后的、分页后的数据对象列表（有排序、有分页）
     * @param currentPage  当前页码
     * @param pageSize     每页记录数
     * @param entityClass 实体类
     * @param condition   参数化sql
     * @param sort        排序条件
     * @param params      sql参数值
     * @return 列表记录
     */
    <T> List<T> querySortedPage(int currentPage, int pageSize, Class<T> entityClass,
                                String condition, String sort, Object... params);

    /**
     * 查询全表、带有部分列值的数据对象列表（未排序、未分页）
     * @param entityClass 实体类
     * @param columnNames  要查询的字段
     * @return 列表记录
     */
    <T> List<T> queryCols(Class<T> entityClass, String columnNames);

    /**
     * 根据条件查询带有指定列值的数据对象列表（未排序，未分页）
     * @param entityClass 实体类
     * @param columnNames  要查询的字段
     * @param condition   参数化sql
     * @param params      sql参数值
     * @return 列表记录
     */
    <T> List<T> queryCols(Class<T> entityClass, String columnNames, String condition, Object... params);

    /**
     * 查询带有指定列值的、排序后的数据对象列表（有排序，未分页）
     * @param entityClass 实体类
     * @param columnNames  要查询的字段
     * @param sort        排序条件
     * @return 列表记录
     */
    <T> List<T> querySortedCols(Class<T> entityClass, String columnNames, String sort);

    /**
     * 根据条件查询带有部分列值的、排序后的数据对象列表（有排序、未分页）
     * @param entityClass 实体类
     * @param columnNames 要查询的字段
     * @param condition 参数化sql
     * @param sort      排序条件
     * @param params   sql参数值
     * @return 列表记录
     */
    <T> List<T> querySortedCols(Class<T> entityClass, String columnNames, String condition,
                                String sort, Object... params);

    /**
     * 根据条件查询带有部分列值的、排序后的、分页后的数据对象列表（有排序、有分页）
     * @param currentPage  当前页码
     * @param pageSize     每页记录数
     * @param entityClass 实体类
     * @param columnNames  要查询的字段
     * @param condition    参数化的sql查询条件
     * @param sort        排序条件
     * @param params      sql参数值
     * @return 列表记录
     */
    <T> List<T> querySortedPageCols(int currentPage, int pageSize, Class<T> entityClass,
                                    String columnNames, String condition, String sort, Object... params);

    /**
     * 查询带有所有列值的、排序后的字段Map列表（有排序，未分页）
     * @param entityClass   实体类
     * @param sort          排序条件
     * @return
     */
    public List<Map<String, Object>> querySortedListMap(Class<?> entityClass, String sort);

    /**
     * 根据条件查询带有所有列值的、排序后的字段Map列表（有排序，未分页）
     * @param entityClass   实体类
     * @param condition     参数化的sql查询条件
     * @param sort          排序条件
     * @param params        sql查询参数值
     * @return
     */
    public List<Map<String, Object>> querySortedListMap(Class<?> entityClass, String condition, String sort, Object... params);

    /**
     * 根据条件查询带有所有列值的、排序后的、分页后的字段Map列表（有排序、有分页）
     * @param currentPage   当前页码
     * @param pageSize      每页记录数
     * @param entityClass   实体类
     * @param condition     参数化的sql查询条件
     * @param sort          排序条件
     * @param params        sql查询参数值
     * @return
     */
    public List<Map<String, Object>> querySortedPageListMap(int currentPage, int pageSize, Class<?> entityClass, String condition, String sort, Object... params);

    /**
     * 查询所有带有指定列值的、排序后的字段Map象列表（有排序，未分页）
     * @param entityClass   实体类
     * @param columnNames   要查询的字段
     * @param sort          排序条件
     * @return
     */
    public List<Map<String, Object>> querySortedColsListMap(Class<?> entityClass, String columnNames, String sort);

    /**
     * 根据条件查询带有部分列值的、排序后的字段Map列表（有排序、未分页）
     * @param entityClass   实体类
     * @param columnNames   要查询的字段
     * @param condition     参数化的sql查询条件
     * @param sort          排序条件
     * @param params        sql查询参数值
     * @return
     */
    public List<Map<String, Object>> querySortedColsListMap(Class<?> entityClass, String columnNames, String condition, String sort, Object... params);

    /**
     * 根据条件查询带有部分列值的、排序后的、分页后的字段Map列表（有排序、有分页）
     * @param currentPage   当前页码
     * @param pageSize      每页记录数
     * @param entityClass   实体类
     * @param columnNames   要查询的字段
     * @param condition     参数化的sql查询条件
     * @param sort          排序条件
     * @param params        sql查询参数值
     * @return
     */
    public List<Map<String, Object>> querySortedPageColsListMap(int currentPage, int pageSize, Class<?> entityClass, String columnNames, String condition, String sort, Object... params);

    /**
     * 根据外部sql查询出Map列表（该方法直接执行外部sql，可用来满足多表联合查询的情况）
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryListMap(String sql, Object... params);
    // ==== 查询记录 END ====

    // ==== 统计数量 START ====
    /**
     * 查询全表记录总数
     * @param entityClass 实体类
     * @return
     */
    long count(Class<?> entityClass);

    /**
     * 根据条件查询记录总数
     * @param entityClass 实体类
     * @param condition   参数化sql
     * @param params      sql参数值
     * @return
     */
    long count(Class<?> entityClass, String condition, Object... params);

    /**
     * 根据条件对某个字段求和
     * @param entityClass 实体类
     * @param field        求和的属性名/表字段名
     * @param condition    参数化sql
     * @param params       sql参数值
     * @return
     */
    public Double sum(Class<?> entityClass, String field, String condition, Object... params);

    /**
     * 对某个字段求和
     * @param entityClass   实体类
     * @param field         求和的属性名/表字段名
     * @return
     */
    public Double sum(Class<?> entityClass, String field);
    // ==== 统计数量 END ====

}