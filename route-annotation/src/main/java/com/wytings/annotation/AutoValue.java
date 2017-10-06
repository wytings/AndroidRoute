package com.wytings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface AutoValue {
    String value() default "";
}
