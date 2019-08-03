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
 * {@link  SrcFileScalarFromJooqRecord} tests suite.
 * 
 * @author skapral
 */
public class SrcFileScalarFromJooqRecordTest extends TestsSuite {
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
    
    private static final Declaration<Scalar> SCALAR_UNDER_TEST = new DeclExplicit<>(
        new Scalar.Value("UserId", int.class),
        "com.test"
    );

    /**
     * Ctor.
     */
    public SrcFileScalarFromJooqRecordTest() {
        super(
            new TestCase(
                "generation of jooq-record-based scalar",
                new AssertAssumingTemporaryDirectory(tmpDir -> 
                    new AssertSourceFileGenerated(
                        new SrcFileScalarFromJooqRecord(
                            SCALAR_UNDER_TEST,
                            new DestToPath(tmpDir)
                        ),
                        tmpDir.resolve("com/test/UserIdFromJooqRecord.java"),
                        String.join(
                            System.lineSeparator(),
                            "package com.test;",
                            "",
                            "import org.jooq.Field;",
                            "import org.jooq.Record;",
                            "",
                            "public class UserIdFromJooqRecord implements UserId {",
                            "  private final Record record;",
                            "",
                            "  private final Field field;",
                            "",
                            "  public UserIdFromJooqRecord(Record record, Field field) {",
                            "    this.record = record;",
                            "    this.field = field;",
                            "  }",
                            "",
                            "  public final int userId() {",
                            "    return record.get(field, int.class);",
                            "  }",
                            "}",
                            ""
                        )
                    )
                )
            )
        );
    }
}
