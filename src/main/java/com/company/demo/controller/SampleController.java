package com.company.demo.controller;

import com.company.demo.auth.PermissionCheck;
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
 *   1. Service类继承自JdbcRapidServiceImpl的部分方法的使用
 *   2. Controller类继承自BaseController的部分方法的使用
 *   3. 鉴权注解PermissionCheck的使用
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

    @PermissionCheck
    @RequestMapping({"view", "show"})
    public String view(){
        String id = getParameterValue("id");
        Sample sample = sampleService.fetch(id);
        setRequestAttribute("sample", sample);
        return "sample/view";
    }

    @RequestMapping("exists")
    public String exists(Model model, HttpServletRequest request){
        String name = request.getParameter("name");
        boolean exists = sampleService.existsName(name);
        model.addAttribute("name", name);
        model.addAttribute("exists", exists);
        return "sample/exists";
    }

    @PermissionCheck
    @RequestMapping("insert")
    public String insert(){
        Sample sample = new Sample();
        sample.setName("张三");
        Integer id = sampleService.insertReturnKey(sample);
        return "redirect:/sample/show.do?id="+id;
    }
}
