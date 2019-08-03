/*-
 * ===========================================================================
 * data-core
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 Kapralov Sergey
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ============================================================================
 */
package com.pragmaticobjects.oo.data.anno;

import io.vavr.collection.List;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Structure annotation.
 * 
 * @author skapral
 */
@Repeatable(Structure.List.class)
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.CLASS)
public @interface Structure {
    /**
     * @return Structure's value
     */
    String value();
    /**
     * @return Structure's parts
     */
    String[] has();
    
    /**
     * Structures list
     */
    @Target(ElementType.PACKAGE)
    @Retention(RetentionPolicy.CLASS)
    @interface List {
        /**
         * @return Items
         */
        Structure[] value();
    }
    
    /**
     * Fixed Structure value for tests
     */
    public static class Value implements Structure {
        private final String value;
        private final io.vavr.collection.List<String> has;

        /**
         * Ctor.
         * @param value Structure's name
         * @param has Structure's parts
         */
        public Value(String value, io.vavr.collection.List<String> has) {
            this.value = value;
            this.has = has;
        }

        /**
         * Ctor.
         * @param value Structure's name
         * @param has Structure's parts
         */
        public Value(String value, String... has) {
            this(value, io.vavr.collection.List.of(has));
        }
        
        @Override
        public final String value() {
            return value;
        }

        @Override
        public final String[] has() {
            return has.toJavaArray(String.class);
        }

        @Override
        public final Class<? extends Annotation> annotationType() {
            return Structure.class;
        }
    }
}
