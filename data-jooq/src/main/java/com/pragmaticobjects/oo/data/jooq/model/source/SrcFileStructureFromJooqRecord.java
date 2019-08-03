/*-
 * ===========================================================================
 * data-jooq
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
package com.pragmaticobjects.oo.data.jooq.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestIndexSimple;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.vavr.collection.List;
import javax.lang.model.element.Modifier;
import org.jooq.Record;


/**
 * Source file for jooq-record-based scalar
 * 
 * @author skapral
 */
public class SrcFileStructureFromJooqRecord extends SrcFileJavaPoet {
    /**
     * Ctor.
     * 
     * @param declaration Declaration
     * @param destination Destination
     * @param manifest Manifest
     */
    public SrcFileStructureFromJooqRecord(Declaration<Structure> declaration, Destination destination, Manifest manifest) {
        super(
            declaration,
            () -> {
                final ManifestIndexSimple<String, Scalar> manifestIndex = new ManifestIndexSimple<>(manifest, Scalar.class, Scalar::value);
                final String packageName = declaration.packageName();
                final String className = declaration.annotation().value() + "FromJooqRecord";
                final ClassName superclass = ClassName.get(
                    declaration.packageName(), declaration.annotation().value() + "Composite"
                );
                final List<FieldSpec> fields = List.of(
                    FieldSpec.builder(Record.class, "record", Modifier.PRIVATE, Modifier.FINAL).build()
                );
                
                CodeBlock.Builder superCall = CodeBlock.builder().add("super(");
                superCall.add(List.of(declaration.annotation().has())
                        .map(superinterface -> {
                            final Declaration<Scalar> scalar = manifestIndex.uniqueByKey(superinterface);
                            final TypeName type = ClassName.get(scalar.packageName(), scalar.annotation().value() + "FromJooqRecord");
                            return type;
                        })
                        .zipWithIndex()
                        .map(tuple -> CodeBlock.of("new $T(record, record.field($L))", tuple._1, tuple._2))
                        .transform(cbl -> CodeBlock.join(cbl, ",")));
                superCall.add(");");
                
                final MethodSpec constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Record.class, "record")
                        .addCode(superCall.build())
                        .build();
                
                return TypeSpec.classBuilder(className)
                        .superclass(superclass)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructor)
                        .build();
            },
            destination
        );
    }
}
