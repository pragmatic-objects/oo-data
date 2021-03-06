/*-
 * ===========================================================================
 * data-scalar
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
package com.pragmaticobjects.oo.data.scalar.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.model.source.javapoet.hacks.Hack;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import javax.lang.model.element.Modifier;
import org.apache.commons.text.WordUtils;

/**
 * Scalar source file.
 * 
 * @author skapral
 */
public class SrcFileScalar extends SrcFileJavaPoet {
    /**
     * Ctor.
     * 
     * @param declaration Scalar declaration
     * @param dest Processing environment
     */
    public SrcFileScalar(Declaration<Scalar> declaration, Destination dest) {
        super(
            declaration,
            () -> {
                final String packageName = declaration.packageName();
                final String ifaceName = declaration.annotation().value();
                return TypeSpec.interfaceBuilder(ifaceName)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(
                            MethodSpec.methodBuilder(WordUtils.uncapitalize(ifaceName))
                                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                .returns(
                                    Hack.extractType(declaration.annotation()::type)
                                )
                                .build()
                        )
                        .build();
            },
            dest
        );
    }
    
    
}
