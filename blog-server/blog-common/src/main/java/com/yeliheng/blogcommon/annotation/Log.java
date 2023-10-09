package com.yeliheng.blogcommon.annotation;

import com.yeliheng.blogcommon.constant.OperateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 模块名称
     */
    String moduleName() default "";

    /**
     * 操作类型
     */
    int operateType() default OperateType.OTHER;
}
