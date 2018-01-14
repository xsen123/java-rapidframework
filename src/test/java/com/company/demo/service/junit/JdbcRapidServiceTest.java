package com.company.demo.service.junit;

import com.company.demo.entity.Detail;
import com.company.demo.entity.Sample;
import com.company.demo.service.IDetailService;
import com.company.demo.service.ISampleService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Jason on 2016-09-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring*.xml"})
@FixMethodOrder(MethodSorters.JVM)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class JdbcRapidServiceTest {

    @Autowired
    ISampleService sampleService;

    @Autowired
    IDetailService detailService;

   @Test
   public void testInsertEntity()
   {
       Sample sample = new Sample();
       sample.setName("testInsertEntity"+ new Random().nextInt(1000));
       Assert.assertTrue(sampleService.insert(sample));
   }

    @Test
    public void testInsertFieldMap()
    {
        Sample sample = new Sample();
        sample.setName("testInsertFieldMap"+ new Random().nextInt(1000));
        Assert.assertTrue(sampleService.insert(sample));
    }

    @Test
    public void testInsertReturnKey()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "testInsertReturnKey"+new Random().nextInt(1000));
        Assert.assertTrue(sampleService.insertReturnKey(map)>0);
    }

    @Test
    public void testInsertFieldReturnKey()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "testInsertFieldReturnKey"+new Random().nextInt(1000));
        Assert.assertTrue(sampleService.insertReturnKey(map)>0);
    }

    @Test
    public void testQueryListMap(){
        List<Map<String, Object>> list;

        list = sampleService.queryListMap("SELECT s.id as sid, s.name, d.id as did, d.sample_id, d.remark from SAMPLE s, detail d where s.id=d.sample_id and s.name=?", "Jason");
        Assert.assertEquals(1, list.size());

        list = sampleService.queryListMap("select * from sample s, detail d where s.id=d.sample_id and s.name=?", "Jason");
        Assert.assertEquals(1, list.size());

        list = sampleService.queryListMap(" select * from sample s, detail d where s.id=d.sample_id and s.name='Jason' ");
        Assert.assertEquals(1, list.size());

        list = sampleService.queryListMap("delete * from sample");
        Assert.assertEquals(null, list);

        list = sampleService.queryListMap("update sample set name='Jason2' where name='Jason'");
        Assert.assertEquals(null, list);

        list = sampleService.queryListMap("insert into sample (name) values ('Jason2')");
        Assert.assertEquals(null, list);
    }

    @Test
    public void testSum()
    {
        Assert.assertEquals(Double.valueOf(4.23), sampleService.sum("money"));
        Assert.assertEquals(Double.valueOf(1.1), sampleService.sum("money", "name=?", "张三"));
        Assert.assertEquals(null, sampleService.sum("money", "name like ?", "测试%"));
    }

    @Test
    public void testInsertEntity4NotColumn()
    {
        Detail detail = new Detail();
        detail.setSampleId(2);
        detail.setSample(sampleService.fetch("name=?", "张三"));
        detail.setRemark("testInsertEntity"+ new Random().nextInt(1000));
        Assert.assertTrue(detailService.insert(detail));
    }

    @Test
    public void testUpdateEntity4NotColumn()
    {
        Detail detail = detailService.fetch(1);
        detail.setRemark("testUpdateEntity"+new Random().nextInt(1000));
        detail.setSample(sampleService.fetch(detail.getSampleId()));
        Assert.assertTrue(detailService.update(detail));
    }

    @Test
    public void testUpdateMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("sample_id",3); // 或 map.put("sampleId",3);
        map.put("remark", "测试");
        Assert.assertTrue(detailService.update(map, "id=?", 9));
    }

    @Test
    public void testUpdateEntity()
    {
        Detail detail = detailService.fetch(1);
        Integer oldSampleId = detail.getSampleId();
        detail.setSampleId(2);
        detail.setRemark("testUpdateEntity"+ new Random().nextInt(1000));
        Assert.assertTrue(detailService.update(detail, "sample_id=?",oldSampleId));
    }

    @Test
    public void testUpdateCols()
    {
        int oldSampleId = 2;
        int newSampleId = 4;
        String remark = "testUpdateCols"+ new Random().nextInt(1000);
        //String columnsUpdateSql = "sample_id=?,remark=?"; // 只能用数据库中的字段名
        String columnsUpdateSql = "sample_id,remark"; // 或者用字段名 "sampleId,remark"
        Object[] params = {newSampleId, remark, oldSampleId};
        int rows = detailService.updateCols(columnsUpdateSql, "sample_id=?",params); // 或者直接传入参数 rows = detailService.updateCols(columns, "sample_id=?",simpleId, remark, 4)
        Assert.assertTrue(rows>=0);
    }

    @Test
    public void testUpdateColsWithArrayFields()
    {
        int oldSampleId = 2;
        int newSampleId = 4;
        String remark = "testUpdateCols"+ new Random().nextInt(1000);
        String[] columns = {"sample_id", "remark"}; // 或者用字段名 {"sampleId", "remark"}
        Object[] params = {newSampleId, remark, oldSampleId};
        int rows = detailService.updateCols(columns, "sample_id=?",params); // 或者直接传入参数 rows = detailService.updateCols(columns, "sample_id=?",simpleId, remark, 4)
        Assert.assertTrue(rows>=0);
    }

    @Test
    public void testQuery()
    {
        List<Detail> detailList = detailService.query("sample_id=?", "2");
        Assert.assertTrue(detailList.size()>=0);
    }

}
