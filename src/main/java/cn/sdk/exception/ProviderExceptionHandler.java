package cn.sdk.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 服务模块统一的异常处理类，主要用来处理Provider的异常，抓住异常，本地打印异常，并对外抛给调用者
 * 该类基于AOP技术，用法是在Provider的spring配置文件中增加切面配置，以anti-brush-center为例，配置如下：
 * <xmp>
   <bean id="providerExceptionHandler" class="cn.sdk.exception.ProviderExceptionHandler"></bean>
   <aop:config>
       <aop:aspect id="aspectExceptionHandling" ref="providerExceptionHandler">
           <aop:around method="around" pointcut="execution(* cn.choumei.anti.service..*(..))"/>
       </aop:aspect>
   </aop:config>
   </xmp>
 * 其中execution指的是需要代理异常处理的service方法集，本例指的是“cn.choumei.anti.service包下的所有类的所有方法”，
 * 各个Provider模块需要根据自己的情况进行配置
 * 
 * @author wubinhong
 */
public class ProviderExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Object around(JoinPoint jp) throws Throwable {
        ProceedingJoinPoint pjp = (ProceedingJoinPoint) jp;
        try {
            Object r = pjp.proceed();
            return r;
        } catch (Exception e) {
            logger.warn("\n\n\n\n********************************** Detail For Method Caused This Exception **********************************");
            // log the name and parameters of method which caused the exception
            Class<? extends Object> clazz = pjp.getTarget().getClass();
            String methodDetail = String.format("%s.%s.%s(%s)", 
                    clazz.getPackage().getName(), clazz.getName(), getMethod(pjp).getName(),
                    JSON.toJSONString(pjp.getArgs(), true));
            logger.error("invoke exception's method detail:\n{}", methodDetail);
            
            // log the statck trace detail
            logger.error("exception stack trace detail: ", e);
            throw e;
        }
    }

    protected <T extends Annotation> Method getMethod(JoinPoint jp) throws SecurityException, NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method r = methodSignature.getMethod();
        return r;
    }
}
