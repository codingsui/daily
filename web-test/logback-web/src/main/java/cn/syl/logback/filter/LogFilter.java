package cn.syl.logback.filter;

import org.aopalliance.intercept.Interceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Component
public class LogFilter implements HandlerInterceptor   {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    private final static String DEFAULT_USERID="uid";


    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        String uid = null;
        if (cookies != null){
            for (Cookie item:cookies) {
                if (item.getName().equals(DEFAULT_USERID)){
                    uid = item.getValue();
                }
            }
        }

        if (StringUtils.isBlank(uid)){
            uid = UUID.randomUUID().toString();
            httpServletResponse.addCookie(new Cookie(DEFAULT_USERID,uid));
        }
        MDC.put(DEFAULT_USERID,uid);
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        MDC.remove(DEFAULT_USERID);
    }
}
