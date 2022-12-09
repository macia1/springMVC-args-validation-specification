package com.specification.mvc.aop;

import com.alibaba.fastjson.JSONObject;
import com.specification.mvc.common.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zenggs
 * @Date 2022/12/9
 * 方便移植可配成xml
 */
@Component
@Aspect
@Slf4j
public class TraceAdvice {

    @Pointcut("@annotation(com.specification.mvc.annotation.LogAdvice)")
    public void log(){};
    // @Around("log()")

    /**
     * 1
     * @Around("execution(* com.crawler.parse.control..*.*(..))")
     * 2
     *<bean id="traceAdvice" class="com.crawler.parse.common.TraceAdvice"/>
     *<aop:config>
     *    <aop:aspect ref="traceAdvice" order="0">
     *        <aop:around method="around"
     *                    pointcut="execution(* com.crawler.parse.control..*.*(..))"
     *                    />
     *    </aop:aspect>
     *</aop:config>
     */
    @Around("execution(* com.specification.mvc.control.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletResponse || args[i] instanceof HttpServletRequest) {
                args[i] = null;
            }
        }
        // 将traceID添加到请求头中
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String traceId = servletRequestAttributes.getRequest().getHeader("traceId") != null ? servletRequestAttributes.getRequest().getHeader("traceId") : TraceContext.getTraceId();
        servletRequestAttributes.getResponse().addHeader("traceId", traceId);
        log.info("访问[{}]{}.{} 接口 start.", traceId, controllerName, methodName);
        Object response = null;
        long costTime = 0;
        try {
            MDC.put("traceId", traceId);
            response = joinPoint.proceed();
            costTime = System.currentTimeMillis() - start;
            log.error("调用服务[{}] {}.{} ," +
                            "花费时长：{}," +
                            "\n 入参: {} " +
                            "\n 出参: {}",
                    traceId, controllerName, methodName, costTime, JSONObject.toJSON(args), JSONObject.toJSON(response));
        } catch (Throwable e) {
            log.error("调用服务[{}] {}.{} , 异常" +
                            "花费时长：{}," +
                            "\n 入参: {} " +
                            "\n 出参: {}",
                    traceId, controllerName, methodName, costTime, JSONObject.toJSON(args), JSONObject.toJSON(response));
            throw e;
        } finally {
            MDC.clear();
            TraceContext.remove();
        }
        return response;
    }
}
