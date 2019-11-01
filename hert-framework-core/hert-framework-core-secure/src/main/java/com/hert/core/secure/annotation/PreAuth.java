package com.hert.core.secure.annotation;

import java.lang.annotation.*;

/**
 * 权限注解 用于检查权限 规定访问权限
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuth {

	String value();

}

