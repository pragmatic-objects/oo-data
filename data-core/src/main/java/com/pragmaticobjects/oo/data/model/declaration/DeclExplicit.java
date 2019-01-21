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
package com.pragmaticobjects.oo.data.model.declaration;

import java.lang.annotation.Annotation;

/**
 * Explicit declaration
 * 
 * @author skapral
 * @param <A> Annotation
 */
public class DeclExplicit<A extends Annotation> implements Declaration<A> {
    private final A annotation;
    private final String packageName;

    /**
     * Ctor.
     * 
     * @param annotation Annotation instance
     * @param packageName Package name
     */
    public DeclExplicit(A annotation, String packageName) {
        this.annotation = annotation;
        this.packageName = packageName;
    }
    
    @Override
    public final A annotation() {
        return annotation;
    }

    @Override
    public final String packageName() {
        return packageName;
    }
}
