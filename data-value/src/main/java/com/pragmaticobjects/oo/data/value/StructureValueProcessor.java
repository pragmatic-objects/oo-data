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
package com.pragmaticobjects.oo.data.value;

import com.pragmaticobjects.oo.data.AbstractProcessor;
import com.pragmaticobjects.oo.data.value.model.source.SrcFileStructureValue;
import io.vavr.collection.List;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Processor for generating value-based implementations for structures
 * 
 * @author skapral
 */
@SupportedAnnotationTypes({
    "com.pragmaticobjects.oo.data.anno.Scalar.List",
    "com.pragmaticobjects.oo.data.anno.Scalar",
    "com.pragmaticobjects.oo.data.anno.Structure.List",
    "com.pragmaticobjects.oo.data.anno.Structure"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StructureValueProcessor extends AbstractProcessor {
    /**
     * Ctor.
     */
    public StructureValueProcessor() {
        super(
            (anno, mani, dest) -> List.empty(),
            (anno, mani, dest) -> List.of(
                new SrcFileStructureValue(
                    anno,
                    dest,
                    mani
                )
            )
        );
    }
}
