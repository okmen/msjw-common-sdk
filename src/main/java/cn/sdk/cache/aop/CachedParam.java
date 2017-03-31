package cn.sdk.cache.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存参数注解，配合{@link CachedMethodGet} 一起使用}
 * @author wubinhong
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CachedParam {

    
}
