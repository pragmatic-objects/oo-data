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

import com.pragmaticobjects.oo.data.helpers.CachingSupplier;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import java.lang.annotation.Annotation;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Simple manifest index with certain indexing function
 * 
 * @param <K> Key type
 * @param <A> annotation's type
 * @author skapral
 */
public class ManifestIndexSimple<K, A extends Annotation> implements ManifestIndex<K, A> {
    private final Supplier<Map<K, List<Declaration<A>>>> indexContents;

    /**
     * Ctor.
     * @param indexFn Index contents
     */
    private ManifestIndexSimple(Supplier<Map<K, List<Declaration<A>>>> indexFn) {
        this.indexContents = indexFn;
    }

    /**
     * Ctor.
     * @param manifest Manifest
     * @param type Type
     * @param indexingFn Indexing function 
     */
    public ManifestIndexSimple(Manifest manifest, Class<A> type, Function<A, K> indexingFn) {
        this(
            new CachingSupplier<>(
                () -> Stream.ofAll(manifest.declarations(type))
                    .groupBy(d -> indexingFn.apply(d.annotation()))
                    .mapValues(Stream::toList)
            )
        );
    }

    @Override
    public final List<Declaration<A>> allByKey(K key) {
        return indexContents.get()
            .get(key)
            .getOrElse(List.empty());
    }

    @Override
    public final Declaration<A> uniqueByKey(K key) {
        return allByKey(key).get();
    }
}
