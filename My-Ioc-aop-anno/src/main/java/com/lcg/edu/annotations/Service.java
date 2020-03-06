package com.lcg.edu.annotations;

import java.lang.annotation.*;

/**
 * @author lichenggang
 * @date 2020/3/5 2:02 下午
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    String value() default "";
}
