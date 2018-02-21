package go.openus.rapidframework.dao.annotation;

import java.lang.annotation.*;

/**
 * 定义表名注解
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    // 表名称，默认为类名的下划线风格字符串。value是应用该注解时的默认属性，可以写作 @Table("xxx")。
    String value() default "";

    // 与value()的作用相同
    String name() default "";

    // 主键列名，默认为id
    String pkColumnName() default "id";

}
