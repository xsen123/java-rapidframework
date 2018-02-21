package go.openus.rapidframework.dao.impl;

import go.openus.rapidframework.dao.IBaseDao;
import go.openus.rapidframework.dao.helper.EntityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

/**
 * Dao层基础类
 */
public abstract class JdbcBaseDaoImpl implements IBaseDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取表名
     *
     * @param entityClass 待获取表名称对应的实体类
     * @return 表名称
     */
    protected String getTableName(Class<?> entityClass) {
        return EntityHelper.getTableName(entityClass);
    }

    /**
     * 获取表的主键字段名
     *
     * @param entityClass 待获取主键字段名称对应的实体类
     * @return 表名称
     */
    protected String getPkColumnName(Class<?> entityClass) {
        return EntityHelper.getPkColumnName(entityClass);
    }

    /**
     * 获取实体类的列映射Map
     *
     * @param entityClass 实体类
     * @return 变量与列字段的映射Map
     */
    protected Map<String, String> getColumnMap(Class<?> entityClass){
        return EntityHelper.getColumnMap(entityClass);
    }

    /**
     * 获取数据库字段名称
     *
     * @param entityClass 实体类
     * @param fieldName 变量名
     * @return 字段名称
     */
    protected String getColumn(Class<?> entityClass, String fieldName) {
        return EntityHelper.getColumnName(entityClass, fieldName);
    }

}
