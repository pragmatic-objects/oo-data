/*-
 * ===========================================================================
 * data-struct
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
package com.pragmaticobjects.oo.data.structure.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestFaked;
import com.pragmaticobjects.oo.data.model.source.AssertAssumingTemporaryDirectory;
import com.pragmaticobjects.oo.data.model.source.AssertSourceFileGenerated;
import com.pragmaticobjects.oo.data.model.source.javapoet.DestToPath;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

/**
 * Tests suite for {@link SrcFileStructure}
 * 
 * @author skapral
 */
public class SrcFileStructureTest extends TestsSuite {
    private static final Manifest TEST_MANIFEST = new ManifestFaked(
        "com.test",
        new Scalar.Value("UserId", int.class),
        new Scalar.Value("UserName", String.class),
        new Scalar.Value("UserLocation", String.class)
    );
    private static final Manifest TEST_MANIFEST2 = new ManifestFaked(
        "com.test",
        new Scalar.Value("UserId", int.class),
        new Scalar.Value("UserName", String.class),
        new Scalar.Value("UserLocation", String.class),
        new Scalar.Value("ZipIndex", String.class),
        new Structure.Value("UserInfo", "UserId", "UserName"),
        new Structure.Value("UserAddress", "UserLocation", "ZipIndex")
    );
    
    /**
     * Ctor.
     */
    public SrcFileStructureTest() {
        super(
            new TestCase(
                "structure generation",
                new AssertAssumingTemporaryDirectory(tmpPath -> 
                    new AssertSourceFileGenerated(
                        new SrcFileStructure(
                            TEST_MANIFEST,
                            new DeclExplicit<>(
                                new Structure.Value(
                                    "User",
                                    "UserId",
                                    "UserName"
                                ),
                                "com.test"
                            ),
                            new DestToPath(tmpPath)
                        ),
                        tmpPath.resolve("com/test/User.java"),
                        String.join(
                            System.lineSeparator(),
                            "package com.test;",
                            "",
                            "public interface User extends UserId, UserName {",
                            "}",
                            ""
                        )
                    )
                )
            ),
            new TestCase(
                "structure must subtype other structure(s) if their set of scalars is subset of original structure",
                new AssertAssumingTemporaryDirectory(tmpPath -> 
                    new AssertSourceFileGenerated(
                        new SrcFileStructure(
                            TEST_MANIFEST2,
                            new DeclExplicit<>(
                                new Structure.Value(
                                    "User",
                                    "UserId",
                                    "UserName",
                                    "UserLocation"
                                ),
                                "com.test"
                            ),
                            new DestToPath(tmpPath)
                        ),
                        tmpPath.resolve("com/test/User.java"),
                        String.join(
                            System.lineSeparator(),
                            "package com.test;",
                            "",
                            "public interface User extends UserId, UserName, UserLocation, UserInfo {",
                            "}",
                            ""
                        )
                    )
                )
            )
        );
    }
}
