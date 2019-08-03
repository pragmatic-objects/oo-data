/*-
 * ===========================================================================
 * data-core
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
package com.pragmaticobjects.oo.data.model.source.javapoet;

import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.source.AssertAssumingTemporaryDirectory;
import com.pragmaticobjects.oo.data.model.source.AssertSourceFileGenerated;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import com.squareup.javapoet.TypeSpec;
import java.lang.annotation.Annotation;

/**
 * Tests for {@link SrcFileJavaPoet}.
 * 
 * @author skapral
 */
public class SrcFileJavaPoetTest extends TestsSuite {
    /**
     * Ctor.
     */
    public SrcFileJavaPoetTest() {
        super(
            new TestCase(
                "simplest java source file generation passes",
                new AssertAssumingTemporaryDirectory(
                    tmpDir -> new AssertSourceFileGenerated(
                        new SrcFileJavaPoet(
                            new DeclExplicit(
                                () -> Annotation.class,
                                "com.pragmaticobjects.test"
                            ),
                            () -> TypeSpec.interfaceBuilder("Test").build(),
                            new DestToPath(tmpDir)
                        ), 
                        tmpDir.resolve("com/pragmaticobjects/test/Test.java"),
                        String.join(
                            System.lineSeparator(),
                            "package com.pragmaticobjects.test;",
                            "",
                            "interface Test {",
                            "}",
                            ""
                        )
                    )
                )
            )
        );
    }
}
