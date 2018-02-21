package go.openus.rapidframework.service.impl;

import go.openus.rapidframework.dao.IRapidDao;
import go.openus.rapidframework.entity.BaseEntity;
import go.openus.rapidframework.service.IRapidService;
import go.openus.rapidframework.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 基于Jdbc Template的快速开发服务类，提供了操作数据常用的方法
 * 重要说明：
 *   1.该类目前特意定义为abstract，以便强制用户定义子Service，并指定泛型处的Entity
 *   2.如果去掉abstract定义，则可通过实例化该类操作Entity数据： IRapidService<Entity> rs = new JdbcRapidServiceImpl<>();
 */
@Transactional
public abstract class JdbcRapidServiceImpl<T extends BaseEntity> implements IRapidService<T> {

    /**
     * 快速开发Dao对象，提供了常用的数据库操作
     */
    @Autowired
    protected IRapidDao rapidDao;

    /**
     * 类定义中指定的泛型类
     */
    private Class<T> entityClass;
    /**
     * 日志对象
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    public JdbcRapidServiceImpl(){
        try{
            entityClass = (Class<T>) ObjectUtils.getParameterizedType(this.getClass(), 0);
        }catch(ClassCastException ex){
            logger.error("类型转换出现异常,可能是该类没有将泛型类具体指定到实际类.", ex);
        }
    }

    @Override
    public boolean insert(BaseEntity entity) {
        return rapidDao.insert(entity);
    }

    @Override
    public boolean insert(final Map<String, Object> fieldMap) {
        return rapidDao.insert(entityClass, fieldMap);
    }

    @Override
    public Integer insertReturnKey(BaseEntity entity) {
        return rapidDao.insertReturnKey(entity);
    }

    @Override
    public Integer insertReturnKey(final Map<String, Object> fieldMap) {
        return rapidDao.insertReturnKey(entityClass, fieldMap);
    }

    @Override
    public boolean delete(Object id) {
        return rapidDao.delete(entityClass, id);
    }

    @Override
    public boolean delete(String condition, Object... params) {
        return rapidDao.delete(entityClass, condition, params);
    }

    @Override
    public boolean update(BaseEntity entity) {
        return rapidDao.update(entity);
    }

    @Override
    public boolean update(BaseEntity entity, String condition, Object... params) {
        return rapidDao.update(entity, condition, params);
    }

    @Override
    public boolean update(Map<String, Object> fieldMap, String condition, Object... params) {
        return rapidDao.update(entityClass, fieldMap, condition, params);
    }

    @Override
    public int updateCols(String[] columns, String condition, Object... params) {
        return rapidDao.updateCols(entityClass, columns, condition, params);
    }

    @Override
    public int updateCols(String columnsUpdateSql, String condition, Object... params) {
        return rapidDao.updateCols(entityClass, columnsUpdateSql, condition, params);
    }

    @Override
    public T fetch(Object id){
        return rapidDao.fetch(entityClass, id);
    }

    @Override
    public T fetch(String condition, Object... params) {
        return rapidDao.fetch(entityClass, condition, params);
    }

    @Override
    public T fetchCols(String columnNames, String condition, Object... params) {
        return rapidDao.fetchCols(entityClass, columnNames, condition, params);
    }

    @Override
    public Map<String, Object> fetchMap(String condition, Object... params) {
        return rapidDao.fetchMap(entityClass, condition, params);
    }

    @Override
    public Map<String, Object> fetchColsMap(String columnNames, String condition, Object... params) {
        return rapidDao.fetchColsMap(entityClass, columnNames, condition, params);
    }

    @Override
    public List<T> query() {
        return rapidDao.query(entityClass);
    }

    @Override
    public List<T> query(String condition, Object... params) {
        return rapidDao.query(entityClass, condition, params);
    }

    @Override
    public List<T> querySorted(String sort) {
        return rapidDao.querySorted(entityClass, sort);
    }

    @Override
    public List<T> querySorted(String condition, String sort, Object... params) {
        return rapidDao.querySorted(entityClass, condition, sort, params);
    }

    @Override
    public List<T> querySortedPage(int currentPage, int pageSize, String condition, String sort, Object... params) {
        return rapidDao.querySortedPage(currentPage, pageSize, entityClass, condition, sort, params);
    }

    @Override
    public List<T> queryCols(String columnNames) {
        return rapidDao.queryCols(entityClass, columnNames);
    }

    @Override
    public List<T> queryCols(String columnNames, String condition, Object... params) {
        return rapidDao.queryCols(entityClass, columnNames, condition, params);
    }

    @Override
    public List<T> querySortedCols(String columnNames, String sort) {
        return rapidDao.querySortedCols(entityClass, columnNames, sort);
    }

    @Override
    public List<T> querySortedCols(String columnNames, String condition, String sort, Object... params) {
        return rapidDao.querySortedCols(entityClass, columnNames, condition, sort, params);
    }

    @Override
    public List<T> querySortedPageCols(int currentPage, int pageSize, String columnNames, String condition, String sort, Object... params) {
        return rapidDao.querySortedPageCols(currentPage, pageSize, entityClass, columnNames, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> querySortedListMap(String sort) {
        return rapidDao.querySortedListMap(entityClass, sort);
    }

    @Override
    public List<Map<String, Object>> querySortedListMap(String condition, String sort, Object... params) {
        return rapidDao.querySortedListMap(entityClass, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> querySortedPageListMap(int currentPage, int pageSize, String condition, String sort, Object... params) {
        return rapidDao.querySortedPageListMap(currentPage, pageSize, entityClass, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> querySortedColsListMap(String columnNames, String sort) {
        return rapidDao.querySortedColsListMap(entityClass, columnNames, sort);
    }

    @Override
    public List<Map<String, Object>> querySortedColsListMap(String columnNames, String condition, String sort, Object... params) {
        return rapidDao.querySortedColsListMap(entityClass, columnNames, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> querySortedPageColsListMap(int currentPage, int pageSize, String columnNames, String condition, String sort, Object... params) {
        return rapidDao.querySortedPageColsListMap(currentPage, pageSize, entityClass, columnNames, condition, sort, params);
    }

    @Override
    public List<Map<String, Object>> queryListMap(String sql, Object... params) {
        return rapidDao.queryListMap(sql, params);
    }

    @Override
    public long count() {
        return rapidDao.count(entityClass);
    }

    @Override
    public long count(String condition, Object... params) {
        return rapidDao.count(entityClass, condition, params);
    }

    @Override
    public Double sum(String field, String condition, Object... params) {
        return rapidDao.sum(entityClass, field, condition, params);
    }

    @Override
    public Double sum(String field) {
        return rapidDao.sum(entityClass, field);
    }

    // ==== 基础方法 START ====
    public Class<T> getEntityClass() {
        return entityClass;
    }
    // ==== 基础方法 END ====

}
