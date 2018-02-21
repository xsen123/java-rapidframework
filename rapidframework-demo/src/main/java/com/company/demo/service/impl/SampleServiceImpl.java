package com.company.demo.service.impl;

import com.company.demo.dao.ISampleDao;
import com.company.demo.entity.Sample;
import com.company.demo.service.ISampleService;
import go.openus.rapidframework.service.impl.JdbcRapidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库增删改查的方法从JdbcRapidServiceImpl继承获得，不用再实现，方法列表详见JdbcRapidServiceImpl
 */
@Service
@Transactional
public class SampleServiceImpl extends JdbcRapidServiceImpl<Sample> implements ISampleService{

    /**
     * 演示使用自定义的Dao类的方法，一般来说，不用自定义Dao类。继承自JdbcRapidServiceImpl的类，自动具备增删改查功能。
     */
    @Autowired
    private ISampleDao sampleDao;

    @Override
    public boolean existsName(String name) {
        // 第一种用法，使用RapidDao对象
        //Sample sample = rapidDao.fetch(Sample.class, "name=?", name);
        //return sample!=null;

        // 第二种用法，使用自定义Dao对象
         return sampleDao.existsName(name);
    }

}
