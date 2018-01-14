package go.openus.rapidframework.dao.annotation;

import java.lang.annotation.*;

/**
 * 定义列名注解
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    // 列字段名，默认为字段名的下划线风格字符串
    String value() default "";

}
