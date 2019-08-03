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
package com.pragmaticobjects.oo.data.value.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.composite.model.source.SrcFileStructureComposite;
import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestFaked;
import com.pragmaticobjects.oo.data.model.source.AssertAssumingTemporaryDirectory;
import com.pragmaticobjects.oo.data.model.source.AssertSourceFileGenerated;
import com.pragmaticobjects.oo.data.model.source.javapoet.DestToPath;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

/**
 * Tests suite for {@link SrcFileStructureComposite}.
 * 
 * @author skapral
 */
public class SrcFileStructureCompositeTest extends TestsSuite {
    private static final Manifest TEST_MANIFEST = new ManifestFaked(
        "com.test",
        new Scalar.Value("UserId", int.class),
        new Scalar.Value("UserName", String.class),
        new Structure.Value(
            "User",
            "UserId",
            "UserName"
        )
    );
    
    private static final Declaration<Structure> STRUCTURE_UNDER_TEST = new DeclExplicit<>(
        new Structure.Value(
            "User",
            "UserId",
            "UserName"
        ),
        "com.test"
    );
    
    /**
     * Ctor.
     */
    public SrcFileStructureCompositeTest() {
        super(
            new TestCase(
                "generation of structure value",
                new AssertAssumingTemporaryDirectory(tmpDir -> 
                    new AssertSourceFileGenerated(
                        new SrcFileStructureComposite(
                            STRUCTURE_UNDER_TEST,
                            new DestToPath(tmpDir),
                            TEST_MANIFEST
                        ),
                        tmpDir.resolve("com/test/UserComposite.java"),
                        String.join(
                            System.lineSeparator(),
                            "package com.test;",
                            "",
                            "import java.lang.String;",
                            "",
                            "public class UserComposite implements User {",
                            "  private final UserId userId;",
                            "",
                            "  private final UserName userName;",
                            "",
                            "  public UserComposite(UserId userId, UserName userName) {",
                            "    this.userId = userId;",
                            "    this.userName = userName;",
                            "  }",
                            "",
                            "  public final int userId() {",
                            "    return this.userId.userId();}",
                            "",
                            "  public final String userName() {",
                            "    return this.userName.userName();}",
                            "}",
                            ""
                        )
                    )
                )
            )
        );
    }
}
