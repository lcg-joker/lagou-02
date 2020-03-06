package com.lcg.edu.annotations;

import java.lang.annotation.*;

/**
 * @author lichenggang
 * @date 2020/3/5 4:33 下午
 * @description
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
}
