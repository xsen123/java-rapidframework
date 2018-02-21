package go.openus.rapidframework.dao.annotation;

import java.lang.annotation.*;

/**
 * 定义表字段中不存在对应列的属性注解
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotColumn {

    // 如果属性需要做自动关联查询，则可指定关联的列字段名，默认为 字段名_id 的下划线风格字符串
    String joinField() default "";

}
