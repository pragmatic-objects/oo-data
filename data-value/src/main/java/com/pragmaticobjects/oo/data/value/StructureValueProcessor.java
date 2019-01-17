/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.value;

import com.pragmaticobjects.oo.data.AbstractProcessor;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.value.model.typeinfo.StructureTypeInformation;
import com.pragmaticobjects.oo.data.model.source.SrcFileForTypeInformation;
import io.vavr.collection.List;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 *
 * @author skapral
 */
@SupportedAnnotationTypes({
    "com.pragmaticobjects.oo.data.anno.Structure",
    "com.pragmaticobjects.oo.data.anno.Structure.List",
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StructureValueProcessor extends AbstractProcessor<Structure> {
    public StructureValueProcessor() {
        super(
            Structure.class,
            (anno, mani, procEnv) -> List.of(
                new SrcFileForTypeInformation(
                    new StructureTypeInformation(anno),
                    anno,
                    procEnv
                )
            )
        );
    }
    
    
    /*public ScalarValueProcessor() {
        super(new AsrcByType<>(Value.class),
            (value, annotatedElement, procEnv) -> new Items<>(
                new GenTaskNewType(
                    new PkgFromElement(annotatedElement),
                    procEnv,
                    new GenTypeClass(
                        new NameAsElementPostfixed(annotatedElement, "Value"),
                        new None<Generic>(),
                        new Items<Type>(
                            new TypeFromTypeElement(annotatedElement)
                        ),
                        new Items<Modifier>(
                            Modifier.PUBLIC
                        ),
                        new TupleFields(annotatedElement),
                        new TupleMethods(annotatedElement),
                        new None<>()
                    )
                )
            )
        );
    }*/
}
