/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.model.source;

import com.pragmaticobjects.oo.data.exception.TupleGenerationException;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.SortedSet;
import java.util.function.Supplier;
import javax.annotation.processing.ProcessingEnvironment;

/**
 *
 * @author skapral
 */
public class SrcFileJavaPoet implements SourceFile {
    private final Declaration<?> declaration;
    private final Supplier<TypeSpec> typeSpec;
    private final ProcessingEnvironment env;

    public SrcFileJavaPoet(Declaration<?> declaration, Supplier<TypeSpec> typeSpec, ProcessingEnvironment env) {
        this.declaration = declaration;
        this.typeSpec = typeSpec;
        this.env = env;
    }

    @Override
    public final void generate() {
        JavaFile javaFile = JavaFile.builder(
            declaration.packageName(),
            typeSpec.get()
        ).build();
        try {
            javaFile.writeTo(env.getFiler());
        } catch (Throwable t) {
            throw new TupleGenerationException(t);
        }
    }
}
