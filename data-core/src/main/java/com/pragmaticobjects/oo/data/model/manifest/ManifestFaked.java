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
package com.pragmaticobjects.oo.data.model.manifest;

import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import io.vavr.collection.List;
import java.lang.annotation.Annotation;

/**
 * Faked instance of manifest for testing.
 * 
 * @author skapral
 */
public class ManifestFaked implements Manifest {
    private final String packageName;
    private final List<Annotation> annotations;

    /**
     * Ctor.
     * 
     * @param packageName Name of faked package
     * @param annotations Annotations
     */
    public ManifestFaked(String packageName, List<Annotation> annotations) {
        this.packageName = packageName;
        this.annotations = annotations;
    }

    /**
     * Ctor.
     * 
     * @param packageName Name of faked package
     * @param annotations Annotations
     */
    public ManifestFaked(String packageName, Annotation... annotations) {
        this(
            packageName,
            List.of(annotations)
        );
    }
    
    @Override
    public final <A extends Annotation> Iterable<Declaration<A>> declarations(Class<A> type) {
        return annotations
                .filter(anno -> type.isAssignableFrom(anno.getClass()))
                .map(anno -> (A) anno)
                .map(anno -> new DeclExplicit<>(anno, packageName));
    }
}
