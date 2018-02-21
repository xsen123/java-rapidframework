package com.company.demo.service;

import com.company.demo.entity.Sample;
import go.openus.rapidframework.service.IRapidService;

/**
 * 数据库增删改查的接口从IRapidService继承获得，不用再声明，接口列表详见IRapidService
 */
public interface ISampleService extends IRapidService<Sample> {

    /**
     * 查询姓名是否存在
     * @param name
     * @return
     */
    boolean existsName(String name);

}
