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
package com.pragmaticobjects.oo.data.model.source;

import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.javapoet.TypeInformation;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Source file, generated from provided {@link TypeInformation}.
 * 
 * @author skapral
 */
public class SrcFileForTypeInformation extends SrcFileJavaPoet {
    /**
     * Ctor.
     * 
     * @param info Type information
     * @param declaration Declaration instance
     * @param env {@link ProcessingEnvironment}, used for saving the file.
     */
    public SrcFileForTypeInformation(TypeInformation info, Declaration<?> declaration, ProcessingEnvironment env) {
        super(
            declaration,
            () -> {
                TypeSpec.Builder builder = TypeSpec.classBuilder(info.name());
                for(FieldSpec field : info.fields()) {
                    builder = builder.addField(field);
                }
                for(MethodSpec method : info.methods()) {
                    builder = builder.addMethod(method);
                }
                return builder.build();
            },
            env
        );
    }
}
