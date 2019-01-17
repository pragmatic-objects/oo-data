/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.scalar.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.hacks.Hack;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.SrcFileJavaPoet;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import org.apache.commons.text.WordUtils;

/**
 *
 * @author skapral
 */
public class SrcFileScalar extends SrcFileJavaPoet {
    
    public SrcFileScalar(Declaration<Scalar> declaration, ProcessingEnvironment env) {
        super(
            declaration,
            () -> {
                final String packageName = declaration.packageName();
                final String ifaceName = declaration.annotation().value();
                final TypeMirror scalarType = Hack.extractType(declaration.annotation()::type);
                return TypeSpec.interfaceBuilder(ifaceName)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(
                            MethodSpec.methodBuilder(WordUtils.uncapitalize(ifaceName))
                                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                .returns(TypeName.get(scalarType))
                                .build()
                        )
                        .build();
            },
            env
        );
    }
    
    
}
