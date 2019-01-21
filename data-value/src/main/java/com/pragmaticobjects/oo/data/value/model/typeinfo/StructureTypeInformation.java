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
package com.pragmaticobjects.oo.data.value.model.typeinfo;

import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.javapoet.TypeInformation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.vavr.collection.List;
import org.apache.commons.text.WordUtils;

import javax.lang.model.element.Modifier;

/**
 * Type information for value-based implementation of structure
 * 
 * @author skapral
 */
public class StructureTypeInformation implements TypeInformation {
    private final Declaration<Structure> declaration;

    /**
     * Ctor.
     * 
     * @param declaration Structure's declaration
     */
    public StructureTypeInformation(Declaration<Structure> declaration) {
        this.declaration = declaration;
    }

    @Override
    public final String name() {
        return declaration.annotation().value() + "Structure";
    }

    @Override
    public final Iterable<TypeName> superinterfaces() {
        final String pkgName = declaration.packageName();
        return List.of(declaration.annotation().has())
            .map(hasStmt -> ClassName.get(pkgName, hasStmt));
    }

    @Override
    public final Iterable<FieldSpec> fields() {
        final String pkgName = declaration.packageName();
        return List.of(declaration.annotation().has())
            .map(hasStmt -> FieldSpec.builder(
                ClassName.get(pkgName, hasStmt),
                WordUtils.uncapitalize(hasStmt),
                Modifier.PRIVATE, Modifier.FINAL
            ).build());
    }

    @Override
    public final Iterable<MethodSpec> methods() {
        final Iterable<FieldSpec> fields = fields();
        MethodSpec.Builder cbuilder = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC);
        for(FieldSpec f : fields) {
            cbuilder = cbuilder
                .addParameter(f.type, f.name)
                .addStatement("this.$L = $L", f.name, f.name);
        }
        final List<MethodSpec> accessors = List.ofAll(fields)
            .map(f -> MethodSpec.methodBuilder(f.name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .returns(f.type)
                .addCode("return this.$L;", f.name)
                .build());
        return accessors.append(
            cbuilder.build()
        );
    }
}
