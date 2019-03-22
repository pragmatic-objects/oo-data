/*
 * The MIT License
 *
 * Copyright 2019 skapral.
 *
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
 */
package com.pragmaticobjects.oo.data.anno;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Import annotation.
 * 
 * @author skapral
 */
@Repeatable(Import.List.class)
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.CLASS)
public @interface Import {
    /**
     * @return Package name
     */
    String value();
    
    /**
     * Import's list
     */
    @Target(ElementType.PACKAGE)
    @Retention(RetentionPolicy.CLASS)
    @interface List {
        /**
         * @return Items
         */
        Import[] value();
    }

    /**
     * Fixed value of import for tests
     */
    public static class Value implements Import {
        private final String value;

        /**
         * Ctor.
         * @param value Package name
         */
        public Value(String value) {
            this.value = value;
        }

        @Override
        public final String value() {
            return value;
        }

        @Override
        public final Class<? extends Annotation> annotationType() {
            return Import.class;
        }
    }
}
