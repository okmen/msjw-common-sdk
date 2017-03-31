package cn.sdk.cache.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用缓存切面{@link CachedAspectService}，用{@link CachedMethodDel}
 * 注解的方法被调用返回时，缓存切面会删除相应代理方法上{@link CachedMethodDel}和{@link CachedParam}的值
 * 
 * @author wubinhong
 * @see CachedMethodGet
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CachedMethodDel {
    
    /**
     * 缓存业务名
     * @see CachedMethodGet#bizName()
     * @return
     */
    String bizName();
    
    /**
     * 是否压缩key
     * @return
     */
    boolean isKeyCompressed() default true;
}
