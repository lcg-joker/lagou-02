package com.lcg.edu.annotations;

import java.lang.annotation.*;

/**
 * @author lichenggang
 * @date 2020/3/5 2:03 下午
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
