package com.yeliheng.blogcommon.annotation;

import com.yeliheng.blogcommon.validator.UploadValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UploadValidator.class)
public @interface Upload {

        String message() default "文件格式或大小不合法";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        /**
         * 上传文件的最大大小 单位：MB -1为不限制
         */
        long maxSize() default 2;

        /**
        * 允许上传文件的类型 用逗号分隔，空为不限制
        */
        String allowType() default "";

        /**
         * 禁止上传文件的类型 用逗号分隔，空为不限制 优先级高于allowType
         */
        String denyType() default "";



}
