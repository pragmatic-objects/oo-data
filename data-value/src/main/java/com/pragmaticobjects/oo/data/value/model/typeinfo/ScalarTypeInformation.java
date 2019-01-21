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

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.hacks.Hack;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.javapoet.TypeInformation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.vavr.collection.List;
import javax.lang.model.element.Modifier;
import org.apache.commons.text.WordUtils;

/**
 * Type information of value-based implementation for scalar.
 * 
 * @author skapral
 */
public class ScalarTypeInformation implements TypeInformation {
    private final Declaration<Scalar> declaration;

    /**
     * Ctor.
     * @param declaration Scalar's declaration
     */
    public ScalarTypeInformation(Declaration<Scalar> declaration) {
        this.declaration = declaration;
    }

    @Override
    public final String name() {
        return declaration.annotation().value() + "Value";
    }

    @Override
    public final Iterable<TypeName> superinterfaces() {
        return List.of(
            ClassName.get(
                declaration.packageName(), declaration.annotation().value()
            )
        );
    }

    @Override
    public final Iterable<FieldSpec> fields() {
        return List.of(
            FieldSpec.builder(
                TypeName.get(Hack.extractType(declaration.annotation()::type)),
                WordUtils.uncapitalize(declaration.annotation().value()),
                Modifier.PRIVATE, Modifier.FINAL
            ).build()
        );
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
