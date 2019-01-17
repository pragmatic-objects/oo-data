/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author skapral
 */
@Repeatable(Cardinality.List.class)
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.CLASS)
public @interface Cardinality {
    String value() default "$default";
    int from() default 0;
    int to() default Integer.MAX_VALUE;
    
    @Target(ElementType.PACKAGE)
    @Retention(RetentionPolicy.CLASS)
    @interface List {
        Cardinality[] value();
    }
}
