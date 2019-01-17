/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.model.manifest;

import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import io.vavr.collection.Stream;
import java.lang.annotation.Annotation;
import javax.lang.model.element.PackageElement;

/**
 *
 * @author skapral
 */
public class ManifestFromPackageElement implements Manifest {
    private final PackageElement element;

    public ManifestFromPackageElement(PackageElement element) {
        this.element = element;
    }

    @Override
    public final <A extends Annotation> Iterable<Declaration<A>> declarations(Class<A> type) {
        return Stream.of(element.getAnnotationsByType(type))
                .map(a -> new DeclExplicit<>(a, element.getQualifiedName().toString()));
    }
}
