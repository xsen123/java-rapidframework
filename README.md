# Java-Rapidframework
### 基于Spring JDBC Template封装的ORM快速开发框架

- 项目的代码包含框架核心代码、使用该框架的示例代码和配置文件，克隆整个工程后即可运行查看效果

- docs目录中存放了最新版rapidframework-core的jar包和用于demo测试的数据库sql文件

- 框架的核心代码为rapidframework-core模块中的文件，可自行打成jar包后引入到项目中使用

- 框架的快速开发主要体现在以下几个方面：
  - **【Entity】**：实体类定义完成后，继承自框架的BaseEntity，并增加一个@Table注解即可实现表与实体类的映射（实体类就是普通的POJO，可使用其他自动化工具快速生成，例如MyBatis、Hibernate的代码生成工具）
  - <del>**【Dao】**</del>：一般情况下，不需要定义操作数据库的Dao文件，Dao对象由框架自动创建和调用，详见JdbcRapidServiceImpl和JdbcRapidDaoImpl；
  - **【Service】**：服务层接口和实现类分别继承自框架的IRapidService&lt;T&gt; 和 JdbcRapidServiceImpl&lt;T&gt;，Service就自动具备了操作实体类T对应的数据表的所有功能；
  - **【Controller】**：控制器继承自框架的BaseController，Controller就自动具备了很多方便实用的方法（可选，但建议使用该方式）；


- 框架使用示例：

  - Entity类

  ```java
  package com.company.demo.entity;

  import go.openus.rapidframework.dao.annotation.Table;
  import go.openus.rapidframework.entity.BaseEntity;

  /**
   * 实体类，可使用以下方式使用注解
   * @Table // 默认表名为驼峰式类名转换为小写+下划线格式后的字符串
   * @Table("sample")
   * @Table(name = "sample")
   * @Table(name = "sample", pkColumnName = "id") // pkColumnName默认为id
   */
  @Table
  public class Sample extends BaseEntity {

      private Integer id ;

      /**
       * 姓名
       */
      private String name;

      /**
       * 金额
       */
      private Double money;

      public Integer getId() {
          return id;
      }
      public void setId(Integer id) {
          this.id = id;
      }
      public String getName() {
          return name;
      }
      public void setName(String name) {
          this.name = name;
      }
      public Double getMoney() {
          return money;
      }
      public void setMoney(Double money) {
          this.money = money;
      }
  }
  ```

  - Service接口

  ```java
  package com.company.demo.service;

  import com.company.demo.entity.Sample;
  import go.openus.rapidframework.service.IRapidService;

  /**
   * 数据库增删改查的接口从IRapidService继承获得，不用再声明，接口列表详见IRapidService
   */
  public interface ISampleService extends IRapidService<Sample> {
    // TODO: 这里声明业务接口...
  }
  ```

  - Service实现类

  ```java
  package com.company.demo.service.impl;

  import com.company.demo.entity.Sample;
  import com.company.demo.service.ISampleService;
  import go.openus.rapidframework.service.impl.JdbcRapidServiceImpl;
  import org.springframework.stereotype.Service;
  import org.springframework.transaction.annotation.Transactional;

  /**
   * 数据库增删改查的方法从JdbcRapidServiceImpl继承获得，不用再实现，方法列表详见JdbcRapidServiceImpl
   */
  @Service
  @Transactional
  public class SampleServiceImpl extends JdbcRapidServiceImpl<Sample> implements ISampleService{
    // TODO: 这里编写业务方法...
  }
  ```

  - Controller类

  ```java
  package com.company.demo.controller;

  import com.company.demo.entity.Sample;
  import com.company.demo.service.ISampleService;
  import go.openus.rapidframework.controller.BaseController;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.RequestMapping;

  import javax.servlet.http.HttpServletRequest;
  import java.util.List;

  /**
   * 示例主要演示了：
   *   1. Service类操作数据库的部分方法
   *   2. Controller类获取和设置参数的部分方法
   */
  @Controller
  @RequestMapping(value="/sample")
  public class SampleController extends BaseController {

      @Autowired
      private ISampleService sampleService;

      @RequestMapping("index")
      public String index(){
          List<Sample> list = sampleService.query();
          setRequestAttribute("count", list.size());
          return "sample/index";
      }

      @RequestMapping("count")
      public String count(String name){
          List<Sample> list = sampleService.query("name=?", name);
          setRequestAttribute("count", list.size());
          return "sample/index";
      }

      @RequestMapping({"view", "show"})
      public String view(){
          String id = getParameterValue("id");
          Sample sample = sampleService.fetch(id);
          setRequestAttribute("sample", sample);
          return "sample/view";
      }

      @RequestMapping("insert")
      public String insert(){
          Sample sample = new Sample();
          sample.setName("张三");
          Integer id = sampleService.insertReturnKey(sample);
          return "redirect:/sample/show.do?id="+id;
      }
  }
  ```
