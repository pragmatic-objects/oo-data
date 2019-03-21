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
package com.pragmaticobjects.oo.data.jooq.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.pragmaticobjects.oo.data.model.source.javapoet.hacks.Hack;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.vavr.collection.List;
import javax.lang.model.element.Modifier;
import org.apache.commons.text.WordUtils;
import org.jooq.Field;
import org.jooq.Record;


/**
 * Source file for jooq-record-based scalar
 * 
 * @author skapral
 */
public class SrcFileScalarFromJooqRecord extends SrcFileJavaPoet {
    /**
     * Ctor.
     * 
     * @param declaration Declaration
     * @param destination Destination
     */
    public SrcFileScalarFromJooqRecord(Declaration<Scalar> declaration, Destination destination) {
        super(
            declaration,
            () -> {
                final String packageName = declaration.packageName();
                final String className = declaration.annotation().value() + "FromJooqRecord";
                final TypeName accessorType = Hack.extractType(declaration.annotation()::type);
                final String accessorName = WordUtils.uncapitalize(declaration.annotation().value());
                final List<ClassName> superifaces = List.of(
                    ClassName.get(
                        declaration.packageName(), declaration.annotation().value()
                    )
                );
                final List<FieldSpec> fields = List.of(
                    FieldSpec.builder(Record.class, "record", Modifier.PRIVATE, Modifier.FINAL).build(),
                    FieldSpec.builder(Field.class, "field", Modifier.PRIVATE, Modifier.FINAL).build()
                );
                final MethodSpec constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Record.class, "record")
                        .addParameter(Field.class, "field")
                        .addStatement("this.$L = $L", "record", "record")
                        .addStatement("this.$L = $L", "field", "field")
                        .build();
                
                final List<MethodSpec> methods = List.of(
                    MethodSpec.methodBuilder(accessorName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .returns(accessorType)
                        .addStatement("return record.get(field, $T.class)", accessorType)
                        .build()
                );
                
                return TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterfaces(superifaces)
                        .addFields(fields)
                        .addMethod(constructor)
                        .addMethods(methods)
                        .build();
            },
            destination
        );
    }
}
