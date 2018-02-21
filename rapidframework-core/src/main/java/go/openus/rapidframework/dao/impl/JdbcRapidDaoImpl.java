package go.openus.rapidframework.dao.impl;

import go.openus.rapidframework.dao.IRapidDao;
import go.openus.rapidframework.dao.helper.EntityHelper;
import go.openus.rapidframework.dao.helper.SqlHelper;
import go.openus.rapidframework.entity.BaseEntity;
import go.openus.rapidframework.utils.MapUtils;
import go.openus.rapidframework.utils.ObjectUtils;
import go.openus.rapidframework.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 快速开发Dao类，实现通用的Dao方法
 */
@Repository
public class JdbcRapidDaoImpl extends JdbcBaseDaoImpl implements IRapidDao {

    // ==== 新增记录 START ====
    @Override
    public boolean insert(BaseEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entity.getClass();
        Map<String, Object> fieldMap = ObjectUtils.getFieldValueMap(entity,true,true);
        return insert(entityClass, fieldMap);
    }

    @Override
    public boolean insert(Class<?> entityClass, final Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            return true;
        }
        String sql = SqlHelper.generateInsertSql(entityClass, fieldMap.keySet());
        int rows = jdbcTemplate.update(sql, fieldMap.values().toArray());
        return rows > 0;
    }

    @Override
    public Integer insertReturnKey(BaseEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entity.getClass();
        Map<String, Object> fieldMap = ObjectUtils.getFieldValueMap(entity,true,true);
        return insertReturnKey(entityClass, fieldMap);
    }

    @Override
    public Integer insertReturnKey(Class<?> entityClass, final Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) { return null; }
        final String sql = SqlHelper.generateInsertSql(entityClass, fieldMap.keySet());

        //自增长ID配置
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId = 0;
        PreparedStatementCreator preparedStatementCreator=new PreparedStatementCreator()
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                int index=0;
                for (Map.Entry<String, Object> fieldEntry : fieldMap.entrySet())
                {
                    index++;
                    Object obj=fieldEntry.getValue();
                    if(fieldEntry.getValue()==null)
                        try {
                            ps.setNull(index,  Types.NULL);
                        }catch (Exception d)
                        {
                            d.printStackTrace();
                        }
                    else ps.setObject(index,fieldEntry.getValue());
                }
                return ps;
            }
        };
        jdbcTemplate.update(preparedStatementCreator,keyHolder);
        autoIncId = keyHolder.getKey().intValue();
        return autoIncId;
    }
    // ==== 新增记录 END ====

    // ==== 删除记录 START ====
    @Override
    public boolean delete(Class<?> entityClass, Object id) {
        String condition = EntityHelper.getPkColumnName(entityClass) + "=?";
        return delete(entityClass, condition, id);
    }

    @Override
    public boolean delete(Class<?> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateDeleteSql(entityClass, condition);
        int rows = jdbcTemplate.update(sql, params);
        return rows > 0;
    }
    // ==== 删除记录 END ====

    // ==== 更新记录 START ====
    @Override
    public boolean update(BaseEntity entityObject) {
        if (entityObject == null) {
            throw new IllegalArgumentException("更新实体对象不能为空");
        }
        Class<?> entityClass = entityObject.getClass();
        String pkName = EntityHelper.getPkColumnName(entityClass);
        Map<String, Object> fieldMap = ObjectUtils.getFieldValueMap(entityObject);
        String condition = pkName + " = ?";
        Object[] params = {ObjectUtils.getFieldValue(entityObject, StringUtils.underlineToCamelHump(pkName))};
        return update(entityClass, fieldMap, condition, params);
    }

    @Override
    public boolean update(BaseEntity entityObject, String condition, Object... params)
    {
        if (entityObject == null) {
            throw new IllegalArgumentException("更新实体对象不能为空");
        }
        Class<?> entityClass = entityObject.getClass();
        String pkName = EntityHelper.getPkColumnName(entityClass);
        Map<String, Object> fieldMap = ObjectUtils.getFieldValueMap(entityObject);
        String lastCondition = pkName + " = ? and " + condition;
        Object[] lastParams = {ObjectUtils.getFieldValue(entityObject, StringUtils.underlineToCamelHump(pkName))};
        List<Object> list = new ArrayList<>();
        list.addAll(Arrays.asList(lastParams));
        if(params!=null) {
            list.addAll(Arrays.asList(params));
        }
        return update(entityClass, fieldMap, lastCondition, list.toArray());
    }

    @Override
    public boolean update(Class<?> entityClass, Map<String, Object> fieldMap, String condition, Object... params) {
        if (MapUtils.isEmpty(fieldMap)) {
            throw new IllegalArgumentException("没有指定需要更新的列");
        }
        String sql = SqlHelper.generateUpdateSql(entityClass, fieldMap, condition);
        int rows = this.jdbcTemplate.update(sql, ArrayUtils.addAll(fieldMap.values().toArray(), params));
        return rows > 0;
    }

    @Override
    public int updateCols(Class<?> entityClass, String[] columns, String condition, Object... params) {
        if(columns==null || columns.length==0){
            throw new IllegalArgumentException("没有指定需要更新的列");
        }
        String sql = SqlHelper.generateUpdateColumnSql(entityClass, columns, condition);
        return this.jdbcTemplate.update(sql, params);
    }

    @Override
    public int updateCols(Class<?> entityClass,String columnsUpdateSql, String condition, Object... params) {
        if(StringUtils.isEmpty(columnsUpdateSql)){
            throw new IllegalArgumentException("没有指定需要更新的列");
        }
        String sql = SqlHelper.generateUpdateSql(entityClass, columnsUpdateSql, condition);
        return this.jdbcTemplate.update(sql, params);
    }
    // ==== 更新记录 END ====

    // ==== 查询记录 START ====
    @Override
    public  <T> T fetch(Class<T> entityClass, Object id){
        return fetch(entityClass, EntityHelper.getPkColumnName(entityClass)+"=?", id);
    }

    @Override
    public <T> T fetch(Class<T> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "", "1");

        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        try{
            //org.springframework.dao.TransientDataAccessResourceException
            return jdbcTemplate.queryForObject(sql,rowMapper,params);
        }catch (EmptyResultDataAccessException e){
            //logger.error("查询单条记录时出现异常："+e.getMessage(), e);
            return null;
        }
    }

    @Override
    public <T> T fetchCols(Class<T> entityClass, String columnNames, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "", "1");
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        try{
            return jdbcTemplate.queryForObject(sql,rowMapper,params);
        }catch (EmptyResultDataAccessException e){
            //logger.error("查询单条记录时出现异常："+e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Map<String, Object> fetchMap(Class<?> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "", "1");
        try{
            return jdbcTemplate.queryForMap(sql, params);
        }catch (EmptyResultDataAccessException e){
            //logger.error("查询单条记录时出现异常："+e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Map<String, Object> fetchColsMap(Class<?> entityClass, String columnNames, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "", "1");
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        try{
            return jdbcTemplate.queryForMap(sql, params);
        }catch (EmptyResultDataAccessException e){
            //logger.error("查询单条记录时出现异常："+e.getMessage(), e);
            return null;
        }
    }
    @Override
    public <T> List<T> query(Class<T> entityClass){
        return query(entityClass,null);
    }
    @Override
    public <T> List<T> query(Class<T> entityClass, String condition, Object... params){
        String sql = SqlHelper.generateSelectSql(entityClass, condition, null);
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper, params);
    }
    @Override
    public <T> List<T> querySorted(Class<T> entityClass, String sort){
        String sql = SqlHelper.generateSelectSql(entityClass, null, sort);
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public <T> List<T> querySorted(Class<T> entityClass, String condition, String sort, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper, params);
    }
    @Override
    public <T> List<T> querySortedPage(int currentPage, int pageSize, Class<T> entityClass, String condition, String sort, Object... params) {
        String sql = SqlHelper.generateSelectSqlForPager(entityClass, condition, sort, currentPage, pageSize);
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    @Override
    public <T> List<T> queryCols(Class<T> entityClass, String columnNames){
        return queryCols(entityClass,columnNames, null);
    }
    @Override
    public <T> List<T> queryCols(Class<T> entityClass, String columnNames, String condition, Object... params){
        String sql = SqlHelper.generateSelectSql(entityClass, condition, null);
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper, params);
    }
    @Override
    public <T> List<T> querySortedCols(Class<T> entityClass, String columnNames, String sort){
        String sql = SqlHelper.generateSelectSql(entityClass, null, sort);
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public <T> List<T> querySortedCols(Class<T> entityClass, String columnNames, String condition, String sort, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper, params);
    }
    @Override
    public <T> List<T> querySortedPageCols(int currentPage, int pageSize, Class<T> entityClass, String columnNames, String condition, String sort, Object... params) {
        String sql = SqlHelper.generateSelectSqlForPager(entityClass, condition, sort, currentPage, pageSize);
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(entityClass);
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    @Override
    public List<Map<String, Object>> querySortedListMap(Class<?> entityClass, String sort){
        return querySortedColsListMap(entityClass, null, null, sort);
    }

    @Override
    public List<Map<String, Object>> querySortedListMap(Class<?> entityClass, String condition, String sort, Object... params){
        return querySortedColsListMap(entityClass, null, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> querySortedPageListMap(int currentPage, int pageSize, Class<?> entityClass, String condition, String sort, Object... params){
        return querySortedPageColsListMap(currentPage, pageSize, entityClass, null, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> querySortedColsListMap(Class<?> entityClass, String columnNames, String sort) {
        return querySortedColsListMap(entityClass, columnNames, null, sort);
    }

    @Override
    public List<Map<String, Object>> querySortedColsListMap(Class<?> entityClass, String columnNames, String condition, String sort, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        return jdbcTemplate.queryForList(sql, params);
    }

    @Override
    public List<Map<String, Object>> querySortedPageColsListMap(int currentPage, int pageSize, Class<?> entityClass, String columnNames, String condition, String sort, Object... params){
        String sql = SqlHelper.generateSelectSqlForPager(entityClass, condition, sort, currentPage, pageSize);
        if(StringUtils.isNotEmpty(columnNames)){ sql = sql.replace("*", columnNames); }
        return jdbcTemplate.queryForList(sql, params);
    }

    @Override
    public List<Map<String, Object>> queryListMap(String sql, Object... params){
        if(StringUtils.isEmpty(sql)) { return null; }
        if(sql.trim().toLowerCase().startsWith("select")==false) { return null; }
        return jdbcTemplate.queryForList(sql, params);
    }
    // ==== 查询记录 END ====

    // ==== 统计数量 START ====
    @Override
    public long count(Class<?> entityClass) {
        String sql = SqlHelper.generateSelectSqlForCount(entityClass, "");
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public long count(Class<?> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSqlForCount(entityClass, condition);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public Double sum(Class<?> entityClass, String field, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSqlForSum(entityClass, field, condition);
        return jdbcTemplate.queryForObject(sql, params, Double.class);
    }

    @Override
    public Double sum(Class<?> entityClass, String field) {
        return sum(entityClass, field, null);
    }

    // ==== 统计数量 END ====

}
