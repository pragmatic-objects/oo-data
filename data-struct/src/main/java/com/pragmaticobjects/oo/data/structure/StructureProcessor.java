/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.structure;

import com.pragmaticobjects.oo.data.AbstractProcessor;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.structure.model.source.SrcFileStructure;
import io.vavr.collection.List;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 *
 * @author skapral
 */
@SupportedAnnotationTypes({
    "com.pragmaticobjects.oo.data.anno.Structure.List",
    "com.pragmaticobjects.oo.data.anno.Structure"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StructureProcessor extends AbstractProcessor<Structure> {

    public StructureProcessor() {
        super(
            Structure.class,
            (decl, mani, procEnv) -> List.of(new SrcFileStructure(mani, decl, procEnv))
        );
    }
    
    
    
    /*public StructureProcessor() {
        super(new AsrcByRepeatableAnnotation<>(
                Structure.class,
                Structure.List.class,
                Structure.List::value
            ),
            (struct, annotatedElement, procEnv) -> new Items(
                new GenTaskNewType(
                    new PkgFromElement(annotatedElement),
                    procEnv,
                    new GenTypeInterface(
                        new NameExtracted(struct::value),
                        new None<Generic>(),
                        new Joined<Type>(
                            new StructureScalars(struct),
                            new Mapped<Structure, Type>(
                                new StructureSupertypes(
                                    struct,
                                    new AllAnnotations<>(
                                        new AsrcByRepeatableAnnotation<>(
                                            Structure.class,
                                            Structure.List.class,
                                            Structure.List::value
                                        ),
                                        annotatedElement
                                    )
                                ),
                                s -> new TypeStructure(
                                    new PkgFromElement(annotatedElement),
                                    s
                                )
                            )
                        ),
                        new Items<Modifier>(
                            Modifier.PUBLIC,
                            Modifier.ABSTRACT
                        ),
                        new None<GeneratedMethod>(),
                        new Items<>(
                            Value.class,
                            Composite.class
                        )
                    )
                )
            )
        );
    }*/
}
