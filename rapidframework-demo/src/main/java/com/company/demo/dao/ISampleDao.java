package com.company.demo.dao;

import go.openus.rapidframework.dao.IBaseDao;

public interface ISampleDao extends IBaseDao {

    /**
     * 判断姓名是否存在
     * @param name
     * @return
     */
    boolean existsName(String name) ;
}
