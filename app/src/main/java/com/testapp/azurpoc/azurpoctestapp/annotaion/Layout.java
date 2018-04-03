package com.testapp.azurpoc.azurpoctestapp.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Layout {
    int layoutRes() default 0;
}

