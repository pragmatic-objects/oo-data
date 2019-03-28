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
package com.pragmaticobjects.oo.data.model.manifest;

import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import io.vavr.collection.Stream;
import java.lang.annotation.Annotation;
import javax.lang.model.element.PackageElement;

/**
 * Manifest, containing all declarations within certain Java package.
 * 
 * @author skapral
 */
public class ManifestFromPackageElement implements Manifest {
    private final PackageElement element;

    /**
     * Ctor.
     * @param element Package element
     */
    public ManifestFromPackageElement(PackageElement element) {
        this.element = element;
    }

    @Override
    public final <A extends Annotation> Iterable<Declaration<A>> declarations(Class<A> type) {
        return Stream.of(element.getAnnotationsByType(type))
                .map(a -> new DeclExplicit<>(a, element.getQualifiedName().toString()));
    }
}
