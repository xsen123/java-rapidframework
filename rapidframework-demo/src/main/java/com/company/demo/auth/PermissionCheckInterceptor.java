package com.company.demo.auth;

import com.company.demo.common.Constants;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class PermissionCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 过滤非HandlerMethod的请求
		HandlerMethod auHandler=null;
		if(handler instanceof HandlerMethod ) {
			auHandler=(HandlerMethod) handler;
		} else {
			return true;
		}

		PermissionCheck permissionCheckMethod = auHandler.getMethodAnnotation(PermissionCheck.class);
		PermissionCheck PermissionCheckCLass =auHandler.getBeanType().getAnnotation(PermissionCheck.class);

		if(   null != permissionCheckMethod || null != PermissionCheckCLass  ){
			if(request.getSession().getAttribute(Constants.SESSION_LOGIN_USER_ID)==null)
			{
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +  request.getContextPath() + "/";
				String url = request.getServletPath() + "?";
				if(request.getQueryString()!=null) { // 有参数的情况
					url += request.getQueryString() + "&";
				} else {
					url += "from=auth&";
				}
				String param = URLEncoder.encode(url,"UTF-8");
				String redirectURL = basePath + "login.do?url=" + param;
				//String redirectURL = basePath + "index.do"; // 演示代码，跳转到首页
				response.sendRedirect(redirectURL);
				return false;
			}
			return true;
		} else {
			return true;
		}
	}

}