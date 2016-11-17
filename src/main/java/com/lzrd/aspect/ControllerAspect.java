package com.lzrd.aspect;

import com.lzrd.Exception.LzrdException;
import com.lzrd.util.CommonUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * Created by ZENGTAO540 on 2015/6/26.
 */

@Aspect
@Component
public class ControllerAspect {

    private static final Log log = LogFactory.getLog(ControllerAspect.class);

    @Around("execution(public * com.lzrd.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String result = null;
        String argsLog = getArgsLog(joinPoint);
        long startTime = System.currentTimeMillis();
        result = doMethod(joinPoint, methodName, argsLog);
        long costTime = System.currentTimeMillis() - startTime;
        log.info(methodName + " finished! cost: " + costTime + "ms" + ". args : " + argsLog);
        return result;
    }

    private String doMethod(ProceedingJoinPoint joinPoint, String methodName, String argsLog) throws Throwable {
        String result;
        try {
            Object resultObject = joinPoint.proceed();
            result = resultObject == null ? null : resultObject.toString();
        } catch (Exception e) {
            log.error(methodName + " Error! args : " + argsLog + "\n" + e.getMessage(), e);
            result = handleException(e);
        }
        return result;
    }

    private String handleException(Exception e) throws LzrdException {
        if(e instanceof LzrdException) {
            LzrdException ae = ((LzrdException) e);
            return CommonUtility.constructResultJson(String.valueOf(ae.getErrorCode()), e.getMessage());
        }
        return CommonUtility.constructResultJson("-1", "服务器内部错误");

    }

    public static String getArgsLog(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuffer argsBuffer = new StringBuffer();
        for(int i = 0; i < args.length; i++){
            argsBuffer.append(String.format("args[%s] = " + args[i] + "   ", i));
        }
        return joinPoint.getSignature().getName() + "'args : " + argsBuffer.toString();

    }

}
