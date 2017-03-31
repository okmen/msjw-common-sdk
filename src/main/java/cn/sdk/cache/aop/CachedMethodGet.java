package cn.sdk.cache.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用缓存切面{@link CachedAspectService}，带有注解{@link CachedMethodGet}
 * 的方法在被调用前，首先会从缓存中取数据，如果有返回，没有则继续调用方法。某方法调用后导致缓存的数据被修改，此方法应该用
 * {@link CachedMethodDel} （prefix与此注解相同） 注解，使缓存数据失效。
 * 
 * @author wubinhong
 * @see CachedMethodDel
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CachedMethodGet {
    
    /**
     * 缓存对应的某个业务名，该名称在相同的缓存模块下应该是唯一的，
     * 同时应该与相应的缓存更新方法上的{@link CachedMethodDel#bizName()}相同
     * @return
     */
    String bizName();
    
    /**
     * 是否压缩key
     * @return
     */
    boolean isKeyCompressed() default true;

    /**
     * 过期时间(sec)，默认一天
     * @return
     */
    int expiration() default 24 * 60 * 60;
}
