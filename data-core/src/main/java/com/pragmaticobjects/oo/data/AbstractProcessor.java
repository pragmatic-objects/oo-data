/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.model.declaration.DeclExplicit;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestCombined;
import com.pragmaticobjects.oo.data.model.manifest.ManifestFromPackageElement;
import com.pragmaticobjects.oo.data.model.source.SourceFile;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import java.lang.annotation.Annotation;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author skapral
 */
public abstract class AbstractProcessor<A extends Annotation> extends javax.annotation.processing.AbstractProcessor {
    private final Class<A> annotationType;
    private final GenerationTaskInference<A> task;

    public AbstractProcessor(Class<A> annotationType, GenerationTaskInference<A> task) {
        this.annotationType = annotationType;
        this.task = task;
    }

    /*private final Manifest manifest;
    private final GenerationTaskInference<A> taskInf;

    public AbstractProcessor(Manifest manifest, GenerationTaskInference<A> taskInf) {
        this.manifest = manifest;
        this.taskInf = taskInf;
    }*/

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
            
            /*
            
            PackageElement element = (PackageElement) annotation.getEnclosingElement();
            A anno = annotation.getAnnotation(annotationType);
            Declaration<A> decl = new DeclExplicit<>(anno, element.getQualifiedName().toString());
            Manifest manifest = new ManifestFromPackageElement(element);
            */
            
            //Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            /*
            for (Element element : elements) {
                annoSource.annotations(element).forEach(anno -> {
                    forEachFoundAnnotation(anno, (E) element);
                });
            }*/
        }
        return false;
    }
    
    /*public final void forEachFoundAnnotation(A annotation, E annotatedElement) {
        System.out.println(this.getClass().getName());
        System.out.println("taskInf = " + taskInf);
        System.out.println("annotatedElement = " + annotatedElement);
        System.out.println("taskInf.tasks(annotation, annotatedElement, processingEnv) = " + taskInf.tasks(annotation, annotatedElement, processingEnv));
        for(SourceFile file : taskInf.tasks(annotation, annotatedElement, processingEnv).iterable()) {
            file.generate();
        }
    }*/
    
    public @FunctionalInterface interface GenerationTaskInference<A extends Annotation> {
        Iterable<SourceFile> sourceFiles(Declaration<A> declaration, Manifest manifest, ProcessingEnvironment procEnv);
    }
}
