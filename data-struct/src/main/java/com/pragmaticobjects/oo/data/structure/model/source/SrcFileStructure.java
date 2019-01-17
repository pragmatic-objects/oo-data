/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.structure.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.source.SrcFileJavaPoet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 *
 * @author skapral
 */
public class SrcFileStructure extends SrcFileJavaPoet {

    public SrcFileStructure(Manifest manifest, Declaration<Structure> declaration, ProcessingEnvironment env) {
        super(
            declaration,
            () -> {
                final Set<String> has = HashSet.of(declaration.annotation().has());
                final String ifaceName = declaration.annotation().value();
                final Iterable<ClassName> baseScalars = Stream
                    .of(declaration.annotation().has())
                    .flatMap(
                        n -> Stream
                            .ofAll(manifest.declarations(Scalar.class))
                            .filter(d -> d.annotation().value().equals(n))
                    )
                    .filter(d -> !d.annotation().value().equals(declaration.annotation().value()))
                    .map(d -> ClassName.get(d.packageName(), d.annotation().value()));
                final Iterable<ClassName> baseStructures = Stream
                    .ofAll(manifest.declarations(Structure.class))
                    .filter(d -> has.containsAll(HashSet.of(d.annotation().has())))
                    .filter(d -> !d.annotation().value().equals(declaration.annotation().value()))
                    .map(d -> ClassName.get(d.packageName(), d.annotation().value()));
                return TypeSpec.interfaceBuilder(ifaceName)
                    .addSuperinterfaces(baseScalars)
                    .addSuperinterfaces(baseStructures)
                    .addModifiers(Modifier.PUBLIC)
                    .build();
            },
            env
        );
    }
    
    
}
