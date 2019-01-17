/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.value;

import com.pragmaticobjects.oo.data.AbstractProcessor;
import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.value.model.typeinfo.ScalarTypeInformation;
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
    "com.pragmaticobjects.oo.data.anno.Scalar",
    "com.pragmaticobjects.oo.data.anno.Scalar.List",
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ScalarValueProcessor extends AbstractProcessor<Scalar> {
    public ScalarValueProcessor() {
        super(Scalar.class,
            (anno, mani, procEnv) -> List.of(new SrcFileForTypeInformation(
                    new ScalarTypeInformation(anno),
                    anno,
                    procEnv
                )
            )
        );
    }
}
