package com.specification.mvc.common;

import com.specification.mvc.annotation.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author zenggs
 * @Date 2022/12/8
 * 权限校验
 */
@Component
@Slf4j
public class AutoInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            log.info("非方法校验放行.");
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        if (method.getAnnotation(Authorization.class) == null){
            log.info("无需验证放行.");
        }
        String token =  request.getHeader("token") == null ? "" : request.getHeader("token").trim();
        if(tokenValidation(token)){
            return true;
        }else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write("{\"code\":"+402+", \"message\":\" 接口未授权. \"}");
            return false;
        }
    }

    private boolean tokenValidation(String token) {
        if ("".equals(token)){
            return false;
        }else {
            if (token.startsWith("ADMIN") && token.endsWith("#")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
