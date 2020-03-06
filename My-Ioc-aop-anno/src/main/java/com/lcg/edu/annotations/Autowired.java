package com.lcg.edu.annotations;

import java.lang.annotation.*;

/**
 * @author lichenggang
 * @date 2020/3/5 2:04 下午
 * @description
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
