/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.model.manifest;

import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import io.vavr.collection.Stream;
import java.lang.annotation.Annotation;

/**
 *
 * @author skapral
 */
public class ManifestCombined implements Manifest {
    private final Iterable<? extends Manifest> manifests;

    public ManifestCombined(Iterable<? extends Manifest> manifests) {
        this.manifests = manifests;
    }

    @Override
    public final <A extends Annotation> Iterable<Declaration<A>> declarations(Class<A> type) {
        return Stream.ofAll(manifests)
                .flatMap(m -> m.declarations(type));
    }
}
