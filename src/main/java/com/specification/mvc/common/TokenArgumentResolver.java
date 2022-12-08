package com.specification.mvc.common;

import com.specification.mvc.annotation.UserToken;
import com.specification.mvc.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @author zenggs
 * @Date 2022/12/8
 */
@Slf4j
@Component
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String ADMIN = "ADMIN#";
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 1.取出参数类型
        Class<?> parameterType = methodParameter.getParameterType();
        if (parameterType.isAssignableFrom(UserInfo.class)){
            for (Annotation annotation : methodParameter.getParameterAnnotations()) {
                if (annotation.annotationType() == UserToken.class){
                    log.info("TokenArgumentResolver parse support.");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        UserInfo userInfo = new UserInfo();
        try{
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            for (Annotation parameterAnnotation : methodParameter.getParameterAnnotations()) {
                if (parameterAnnotation.annotationType() == UserToken.class){
                    String token = request.getHeader("token");
                    return getUserInfoByToken(token);
                }
            }
        }catch (Exception e){
            log.error("TokenArgumentResolver args parse error.");
        }
        return userInfo;
    }

    private Object getUserInfoByToken(String token) {
        if (ADMIN.equals(token)){
            return new UserInfo("SupperMan","1888888888","123@NAS.com");
        }else {
            return new UserInfo("customer","123333","null");
        }
    }
}
