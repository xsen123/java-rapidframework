package com.company.demo.dao.impl;

import com.company.demo.dao.ISampleDao;
import go.openus.rapidframework.dao.impl.JdbcBaseDaoImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 * 除非需要编写特殊的数据库操作语句，否则不用自行创建Dao类，
 */
@Repository
public class SampleDaoImpl extends JdbcBaseDaoImpl implements ISampleDao {

    /**
     * 演示代码：直接继承自JdbcBaseDaoImpl后，调用jdbcTemplate方法；也可继承自JdbcRapidDaoImpl，这样能就自动具备了通用的增删改查功能
     * 判断姓名是否存在
     * @param name
     * @return
     */
    @Override
    public boolean existsName(String name) {
        String sql = "select 1 from sample where name=? limit 1";
        try{
            return jdbcTemplate.queryForMap(sql, name)!=null;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }

}
