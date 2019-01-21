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
package com.pragmaticobjects.oo.data.hacks;

import java.util.function.Supplier;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * A nasty utility class with a set of dirty, but necessary hacks.
 * 
 * @author skapral
 */
public class Hack {
    private Hack() {}
    
    /**
     * In annotation processors, retrieving an annotation field with {@link Class} type
     * ends with exception. There is no known way of working this around, but this
     * hack at least provides means to obtaining {@link TypeMirror} instance instead.
     * 
     * @param supplier Method reference to the annotation field of type {@link Class}
     * @return {@link TypeMirror} instance.
     */
    public static TypeMirror extractType(Supplier<Class> supplier) {
        try {
            supplier.get();
            throw new RuntimeException(
                "If you see this exception, something in this cursed "
                + "world changed to better side and it is now "
                + "possible to extract "
                + "a class value from annotation. "
                + "Tell us to rewrite this ugly hack by reporting an issue."
            );
        } catch(MirroredTypeException ex) {
            return ex.getTypeMirror();
        }
    }
}
