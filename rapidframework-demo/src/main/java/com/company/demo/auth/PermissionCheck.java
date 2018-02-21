package com.company.demo.auth;

import java.lang.annotation.*;

/**
 * 权限检查注解，可用于方法和类型
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionCheck {
    public String value() default "";
}
