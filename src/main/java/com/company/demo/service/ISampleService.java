package com.company.demo.service;

import com.company.demo.entity.Sample;
import go.openus.rapidframework.service.IRapidService;

public interface ISampleService extends IRapidService<Sample> {

    /**
     * 查询姓名是否存在
     * @param name
     * @return
     */
    boolean existsName(String name);

}
