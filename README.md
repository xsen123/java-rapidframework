# Java-rapidframework
### 基于spring jdbc template封装的ORM快速开发框架

- 项目的代码包含了如何使用该框架的示例代码和配置文件

- docs目录中存放了用于测试的示例数据库sql文件和最新版rapidframework的jar包

- 框架的核心代码为go.openus.rapidframework中的所有文件，可自行打成jar包后引入到第三方项目中使用

- 框架的快速开发主要体现在两个方面：

 - 项目中的业务Service接口和类只需要分别继承自IRapidService&lt;T&gt; 和 JdbcRapidServiceImpl&lt;T&gt;，Service就自动具备了增删改查功能，用以操作实体T对应的数据库表；

 - 项目中的Controller类只需要继承自BaseController，Controller就自动具备了很多方便实用的方法；

- Service使用示例：
  - Service接口

  ```java
  package com.company.demo.service;

  import com.company.demo.entity.Sample;
  import go.openus.rapidframework.service.IRapidService;

  /**
   * 对数据库增删改查等操作的接口自动继承获得，不用再声明
   */
  public interface ISampleService extends IRapidService<Sample> {
    // TODO: 这里声明业务接口
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
   * 对数据库增删改查等操作的方法自动继承获得，不用再实现
   */
  @Service
  @Transactional
  public class SampleServiceImpl extends JdbcRapidServiceImpl<Sample> implements ISampleService{
    // TODO: 这里编写业务方法
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
