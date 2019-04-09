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
import com.pragmaticobjects.oo.data.anno.Import;
import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestCombined;
import com.pragmaticobjects.oo.data.model.manifest.ManifestFromPackageElement;
import com.pragmaticobjects.oo.data.model.source.SourceFile;
import com.pragmaticobjects.oo.data.model.source.javapoet.DestDeduplicated;
import com.pragmaticobjects.oo.data.model.source.javapoet.DestFromProcessingEnvironment;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import java.lang.annotation.Annotation;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Base annotation processor for all data generators.
 * 
 * @author skapral
 */
@NotAtom
public abstract class AbstractProcessor extends javax.annotation.processing.AbstractProcessor {
    private final GenerationTaskInference<Scalar> scalarTasks;
    private final GenerationTaskInference<Structure> structureTasks;

    /**
     * Ctor.
     * 
     * @param scalarTasks Scalar tasks
     * @param structureTasks Structure tasks
     */
    public AbstractProcessor(GenerationTaskInference<Scalar> scalarTasks, GenerationTaskInference<Structure> structureTasks) {
        this.scalarTasks = scalarTasks;
        this.structureTasks = structureTasks;
    }

    @Override
    public boolean process(java.util.Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(this.getClass().getName());
        for(PackageElement pkg : HashSet.ofAll(annotations)
                .flatMap(anno -> roundEnv.getElementsAnnotatedWith(anno))
                .map(e -> (PackageElement) e)) {
            System.out.println(">>>>" + pkg);
            final List<Import> imports = List.of(pkg.getAnnotationsByType(Import.class));
            final List<PackageElement> importPackages = imports
                    .map(i -> processingEnv.getElementUtils().getPackageElement(i.value()));
            final Manifest localManifest = new ManifestFromPackageElement(pkg);
            final Manifest contextManifest = new ManifestCombined(
                importPackages
                    .<Manifest>map(ManifestFromPackageElement::new)
                    .append(localManifest)
            );
            Destination dest = new DestDeduplicated(imports, processingEnv, new DestFromProcessingEnvironment(processingEnv));
            System.out.println("AAA");
            for(Declaration<Scalar> declaration : List.ofAll(contextManifest.declarations(Scalar.class))) {
                System.out.println(declaration.annotation());
                scalarTasks.sourceFiles(
                    declaration,
                    contextManifest,
                    dest
                ).forEach((sourceFile) -> {
                    sourceFile.generate();
                });
            }
            System.out.println("BBB");
            for(Declaration<Structure> declaration : List.ofAll(contextManifest.declarations(Structure.class))) {
                System.out.println(declaration.annotation());
                structureTasks.sourceFiles(
                    declaration,
                    contextManifest,
                    dest
                ).forEach((sourceFile) -> {
                    sourceFile.generate();
                });
            }
        }
        return false;
    }
    
    /**
     * Generation task, aka a set of {@link SourceFile} to generate
     * @param <A> type of processed annotation
     */
    public @FunctionalInterface interface GenerationTaskInference<A extends Annotation> {
        /**
         * @param declaration Declaration to process
         * @param manifest Manifest with all available declarations from the context
         * @param dest Destination
         * @return The list of source files
         */
        Iterable<SourceFile> sourceFiles(Declaration<A> declaration, Manifest manifest, Destination dest);
    }    
}
