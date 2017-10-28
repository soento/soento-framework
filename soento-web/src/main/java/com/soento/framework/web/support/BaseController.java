package com.soento.framework.web.support;

import com.soento.framework.core.support.MessageSourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class BaseController {

    @Autowired
    protected MessageSourceAccessor msa;

    /**
     * 获取HttpServletRequest对象
     *
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    /**
     * 获取HttpServletResponse对象
     *
     * @return HttpServletResponse对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getResponse();
        }
        return null;
    }

    /**
     * 获取HttpSession对象
     *
     * @return HttpSession对象
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getSession();
        }
        return null;
    }

    /**
     * 获取Locale对象
     *
     * @return Locale对象
     */
    public static Locale getLocale() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getLocale();
        }
        return null;
    }
}
