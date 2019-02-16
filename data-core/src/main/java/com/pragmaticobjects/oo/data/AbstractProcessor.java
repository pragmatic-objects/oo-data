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
package com.pragmaticobjects.oo.data;

import com.pragmaticobjects.oo.atom.anno.NotAtom;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestCombined;
import com.pragmaticobjects.oo.data.model.manifest.ManifestFromPackageElement;
import com.pragmaticobjects.oo.data.model.source.SourceFile;
import io.vavr.collection.HashSet;
import java.lang.annotation.Annotation;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Base annotation processor for all data generators.
 * 
 * @author skapral
 * @param <A> Annotation
 */
@NotAtom
public abstract class AbstractProcessor<A extends Annotation> extends javax.annotation.processing.AbstractProcessor {
    private final Class<A> annotationType;
    private final GenerationTaskInference<A> task;

    /**
     * Ctor.
     * 
     * @param annotationType Type of annotation
     * @param task Generation task
     */
    public AbstractProcessor(Class<A> annotationType, GenerationTaskInference<A> task) {
        this.annotationType = annotationType;
        this.task = task;
    }

    @Override
    public boolean process(java.util.Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getElementUtils();
        for (TypeElement annotation : annotations) {
            Manifest manifest = HashSet.of(roundEnv)
                    .flatMap(env -> env.getElementsAnnotatedWith(annotation))
                    .map(e -> (PackageElement) e)
                    .map(e -> new ManifestFromPackageElement(e))
                    .transform(ms -> new ManifestCombined(ms));
           
            for(Declaration<A> declaration : manifest.declarations(annotationType)) {
                task.sourceFiles(declaration, manifest, processingEnv).forEach(SourceFile::generate);
            }
        }
        return false;
    }
    
    /**
     * Generation task, aka a set of {@link SourceFile} to generate
     * 
     * @param <A> Annotation
     */
    public @FunctionalInterface interface GenerationTaskInference<A extends Annotation> {
        /**
         * @param declaration Declaration to process
         * @param manifest Manifest with all available declarations from the context
         * @param procEnv Processing environment
         * @return The list of source files
         */
        Iterable<SourceFile> sourceFiles(Declaration<A> declaration, Manifest manifest, ProcessingEnvironment procEnv);
    }
}
