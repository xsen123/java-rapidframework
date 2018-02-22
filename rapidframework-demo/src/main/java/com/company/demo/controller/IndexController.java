package com.company.demo.controller;

import com.company.demo.common.Constants;
import go.openus.rapidframework.controller.BaseController;
import go.openus.rapidframework.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class IndexController extends BaseController {

    @RequestMapping("index")
    public String index(){
        setRequestAttribute("login", getSessionValue(Constants.SESSION_LOGIN_USER_ID));
        return "index/index";
    }

    @RequestMapping("login")
    public String login(){
        setSessionAttribute(Constants.SESSION_LOGIN_USER_ID, "somebody");
        String url = getParameterValue("url");
        if(StringUtils.isEmpty(url)){
            url = "/index.do";
        }
        return "redirect:"+url;
    }

}
