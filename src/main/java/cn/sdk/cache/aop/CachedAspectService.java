package cn.sdk.cache.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sdk.cache.ICacheManger;

/**
 * 面向切点的缓存服务类，用于缓存方法返回值，要使用该缓存服务，需要在配置文件里加上服务bean配置，以seller-center为例，配置如下：
 * <xmp>
    <!-- enable aspect based cached service -->
    <aop:aspectj-autoproxy  proxy-target-class="true"/>
    <bean class="cn.sdk.cache.aop.CachedAspectService">
        <property name="moduleName" value="seller_center"></property>
        <property name="iCacheManger" ref="jedisCacheManagerImpl"></property>
    </bean>
   </xmp>
 * @author wubinhong
 */
@Aspect
public class CachedAspectService {

    public static String UNDERLINE = "_";

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 缓存模块名称（前缀）*/
    private String moduleName;
    
    private ICacheManger<Object> iCacheManger;

    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public ICacheManger<Object> getiCacheManger() {
        return iCacheManger;
    }
    public void setiCacheManger(ICacheManger<Object> iCacheManger) {
        this.iCacheManger = iCacheManger;
    }
    /**
     * 调用被{@link CachedAspectService}
     * 注解的方法前，此方法根据注解的参数，以及带有{@link CachedParam}注解的方法参数，从缓存中获取结果，如果有则返回，没有则调用注解的方法，并返回其值。
     * 
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(cn.sdk.cache.aop.CachedMethodGet)")
    public Object around(JoinPoint jp) throws Throwable {
        
        logger.debug("aop cached service cached action invoked...");
        ProceedingJoinPoint pjp = (ProceedingJoinPoint) jp;
        
        Method method = getMethod(pjp, CachedMethodGet.class);
        CachedMethodGet cachedMethodGet = method.getAnnotation(CachedMethodGet.class);
        if(cachedMethodGet != null) {
            String key = getKey(method, pjp.getArgs(), moduleName,
                    cachedMethodGet.bizName(), cachedMethodGet.isKeyCompressed());
            
            Object r = iCacheManger.get(key);
            logger.debug("cached info: <== {} : {}", key, r);
            if(r == null) {
                r = pjp.proceed();
                logger.debug("cached info: ==> {} : {}", key, r);
                if(r != null)
                    iCacheManger.set(key, r, cachedMethodGet.expiration());
            }
            return r;
        } else {
            return pjp.proceed();
        }
        
        
    }
    /**
     * 根据模块前缀、方法全名、参数列表构造缓存的key
     * construct a cached key, with module prefix, biz name and parameters, e.g. 
     * moduleName_bizName_cachedParam1Val_cachedParam2Val_cachedParam3Val
     * @param pjp
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    private String getKey(Method method, Object[] args, String moduleName, String bizName, 
            boolean isKeyCompressed) throws SecurityException, NoSuchMethodException {
        
        List<Object> keys = new ArrayList<Object>();

        // module prefix
        keys.add(moduleName);
        // biz name
        keys.add(bizName);
        // parameter with @CachedParam
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int parameterIdx = 0; parameterIdx < parametersAnnotations.length; parameterIdx++) {
            Annotation[] parameterAnnotations = parametersAnnotations[parameterIdx];
            for(Annotation parameterAnnotation : parameterAnnotations) {
                if(parameterAnnotation instanceof CachedParam) {
                    Object value = args[parameterIdx];
                    if(value.getClass().isArray()) {    // for variable parameters
                        Object[] arr = (Object[]) value;
                        for(Object obj : arr) {
                            keys.add(obj);
                        }
                    } else {
                        keys.add(value);
                    }
                }
            }
        }
        
        String key = StringUtils.join(keys, UNDERLINE);
        logger.debug("cached info: raw key[{}]", key);
        String newKey = key;
        if(isKeyCompressed) {
            newKey = DigestUtils.md5Hex(key);
            logger.debug("cached info: compress key {} --> {}", key, newKey);
        }
        return newKey;
    }
    
    

    /**
     * 调用被{@link CachedMethodDel}
     * 注解的方法时，返回值满足一下条件之一时：
     * 
     * <li>1)返回值类型是void；
     * <li>2)返回值类型不是void并且值不是 null 或 0 <br>
     * 
     * 会删除被{@link CachedMethodGet}注解的缓存数据
     * 
     * @param pjp
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @AfterReturning(pointcut = "@annotation(cn.sdk.cache.aop.CachedMethodDel)", returning = "retVal")
    public void afterReturn(JoinPoint jp, Object retVal) throws Throwable {
        
        logger.debug("aop cached service delete action invoked...");
        
        boolean changed = true;
        Class<?> retType = ((MethodSignature) jp.getSignature()).getReturnType();
        if (retType != Void.TYPE) {
            if (retVal == null)
                changed = false;
            else {
                if (retType == byte.class || retType == Byte.class) {
                    if ((Byte) retVal == 0)
                        changed = false;
                } else if (retType == short.class || retType == Short.class) {
                    if ((Short) retVal == 0)
                        changed = false;
                } else if (retType == int.class || retType == Integer.class) {
                    if ((Integer) retVal == 0)
                        changed = false;
                } else if (retType == long.class || retType == Long.class) {
                    if ((Long) retVal == 0)
                        changed = false;
                } else if (retType == float.class || retType == Float.class) {
                    if ((Float) retVal == 0)
                        changed = false;
                } else if (retType == double.class || retType == Double.class) {
                    if ((Double) retVal == 0)
                        changed = false;
                }
            }
        }

        ProceedingJoinPoint pjp = (ProceedingJoinPoint) jp;
        Method method = getMethod(pjp, CachedMethodDel.class);
        CachedMethodDel cachedMethodDel = method.getAnnotation(CachedMethodDel.class);
        String key = getKey(method, pjp.getArgs(), moduleName,
                cachedMethodDel.bizName(), cachedMethodDel.isKeyCompressed());
        
        if (changed) {
            iCacheManger.del(key);
        }
        
    }

    /**
     * 获取使用参数<I>annotation</I>标注的目标方法
     * 
     * @param jp
     * @param annotation
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    private <T extends Annotation> Method getMethod(JoinPoint jp, Class<T> annotation) throws SecurityException, NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method r = methodSignature.getMethod();
        T anno = r.getAnnotation(annotation);

        if (anno == null) {
            String methodName = methodSignature.getName();
            r = jp.getTarget().getClass().getDeclaredMethod(methodName, r.getParameterTypes());
        }
        return r;
    }

}
