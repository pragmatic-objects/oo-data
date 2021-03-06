/*-
 * ===========================================================================
 * data-struct
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
package com.pragmaticobjects.oo.data.structure.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import javax.lang.model.element.Modifier;

/**
 * Structure source file
 * 
 * @author skapral
 */
public class SrcFileStructure extends SrcFileJavaPoet {
    /**
     * Ctor.
     * @param manifest Manifest
     * @param declaration Declaration
     * @param dest Destination
     */
    public SrcFileStructure(Manifest manifest, Declaration<Structure> declaration, Destination dest) {
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
            dest
        );
    }
    
    
}
