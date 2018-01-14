package go.openus.rapidframework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 快速开发Controller基础类
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据key获取request的String类型的参数值
     * @param key
     * @return
     */
    protected String getParameterValue(String key) { return RequestHolder.getParameterValue(key); }

    /**
     * 根据key获取request的Object类型的参数值
     * @param key
     * @return
     */
    protected Object getParameter(String key) { return RequestHolder.getParameterMap().get(key); }

    /**
     * 获取request参数值Map
     * @return
     */
    protected Map getParameterMap() { return RequestHolder.getParameterMap(); }

    /**
     * 获取request参数值
     * @param key
     * @return
     */
    protected String[] getParameterValues(String key) { return RequestHolder.getParameterValues(key); }

    /**
     * 设置request属性值
     * @param key
     * @param value
     */
    protected void setRequestAttribute(String key,Object value) { RequestHolder.setAttribute(key, value); }

    /**
     * 清除request节点
     * @param key
     */
    protected void removeRequestAttribute(String key) { RequestHolder.removeAttribute(key); }

    /**
     * 获取session节点值
     * @param key 节点键名
     * @return 值
     */
    protected Object getSessionValue(String key) { return SessionHolder.getAttribute(key); }

    /**
     * 设置session属性值
     * @param key
     * @param value
     */
    protected void setSessionAttribute(String key,Object value) { SessionHolder.setAttribute(key, value); }

    /**
     * 清除session节点
     * @param key
     */
    protected void removeSessionAttribute(String key) { SessionHolder.removeAttribute(key); }

    /**
     * 销毁session
     */
    protected void invalidateSession() { SessionHolder.invalidate(); }

    /**
     * 获取request对象
     * @return
     */
    protected HttpServletRequest getRequest() { return RequestHolder.getRequest(); }

    /**
     * 获取session对象
     * @return
     */
    protected HttpSession getSession() { return SessionHolder.getSession(); }

    // ====

    /**
     * request封装类
     */
    protected static class RequestHolder {
        public static HttpServletRequest getRequest() { return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); }
        public static String getParameterValue(String name) { return getRequest().getParameter(name); }
        public static String[] getParameterValues(String name) { return getRequest().getParameterValues(name); }
        public static Map getParameterMap() { return getRequest().getParameterMap(); }
        public static void setAttribute(String name,Object obj) { getRequest().setAttribute(name,obj); }
        public static void removeAttribute(String name) { getRequest().removeAttribute(name); }
    }

    /**
     * session封装类
     */
    protected static class SessionHolder {
        public static HttpSession getSession() { return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(); }
        public static Object getAttribute(String name) { return getSession().getAttribute(name); }
        public static void setAttribute(String name, Object obj) { getSession().setAttribute(name, obj); }
        public static void removeAttribute(String name) { getSession().removeAttribute(name); }
        public static void invalidate() { getSession().invalidate(); }
    }

}
