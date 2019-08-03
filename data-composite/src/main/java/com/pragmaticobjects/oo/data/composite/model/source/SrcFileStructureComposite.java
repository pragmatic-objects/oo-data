/*-
 * ===========================================================================
 * data-composite
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
package com.pragmaticobjects.oo.data.composite.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestIndexSimple;
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

/**
 *
 * @author skapral
 */
public class SrcFileStructureComposite extends SrcFileJavaPoet {
    /**
     * Ctor.
     * @param declaration Declaration
     * @param dest Destination
     * @param manifest Manifest
     */
    public SrcFileStructureComposite(Declaration<Structure> declaration, Destination dest, Manifest manifest) {
        super(
            declaration,
            () -> {
                final ManifestIndexSimple<String, Scalar> manifestIndex = new ManifestIndexSimple<>(manifest, Scalar.class, Scalar::value);
                final String name = declaration.annotation().value() + "Composite";
                final TypeName type = ClassName.get(declaration.packageName(), declaration.annotation().value());
                final List<FieldSpec> fields = List.of(declaration.annotation().has())
                    .map(superinterface -> {
                        final Declaration<Scalar> scalar = manifestIndex.uniqueByKey(superinterface);
                        final String fieldName = WordUtils.uncapitalize(superinterface);
                        final TypeName fieldType = ClassName.get(scalar.packageName(), superinterface);
                        return FieldSpec.builder(
                                fieldType, fieldName,
                                Modifier.PRIVATE, Modifier.FINAL
                        ).build();
                    });
                
                final MethodSpec constructor = fields.foldLeft(
                    MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC),
                    (cbuilder, f) -> cbuilder
                        .addParameter(f.type, f.name)
                        .addStatement("this.$L = $L", f.name, f.name)
                ).build();
                
                final List<MethodSpec> accessors = List.of(declaration.annotation().has())
                    .map(superinterface -> {
                        final Declaration<Scalar> scalar = manifestIndex.uniqueByKey(superinterface);
                        final TypeName returnType = Hack.extractType(scalar.annotation()::type);
                        final String methodName = WordUtils.uncapitalize(superinterface);
                        return MethodSpec.methodBuilder(methodName)
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .returns(returnType)
                            .addCode("return this.$L.$L();", methodName, methodName)
                            .build();
                    });
                
                return TypeSpec.classBuilder(name)
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterface(type)
                        .addFields(fields)
                        .addMethod(constructor)
                        .addMethods(accessors)
                        .build();
            },
            dest
        );
    }
}
