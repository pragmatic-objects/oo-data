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
package com.pragmaticobjects.oo.data.model.source.javapoet;

import com.squareup.javapoet.JavaFile;
import java.io.IOException;
import javax.annotation.processing.ProcessingEnvironment;

/**
 * Destination, based on {@link ProcessingEnvironment} instance
 * 
 * @author skapral
 */
public class DestFromProcessingEnvironment implements Destination {
    private final ProcessingEnvironment env;

    /**
     * Ctor.
     * @param env Processing environment
     */
    public DestFromProcessingEnvironment(ProcessingEnvironment env) {
        this.env = env;
    }

    @Override
    public final void persist(JavaFile file) {
        try {
            file.writeTo(env.getFiler());
        } catch(IOException ex) {
            throw new RuntimeException(
                String.format("Attempt to save java file %s failed", file.toJavaFileObject().getName()),
                ex
            );
        }
        
    }
}
