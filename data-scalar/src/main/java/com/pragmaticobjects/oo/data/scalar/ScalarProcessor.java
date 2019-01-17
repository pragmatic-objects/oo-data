/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.scalar;

import com.pragmaticobjects.oo.data.AbstractProcessor;
import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.scalar.model.source.SrcFileScalar;
import java.util.List;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 *
 * @author skapral
 */
@SupportedAnnotationTypes({
    "com.pragmaticobjects.oo.data.anno.Scalar.List",
    "com.pragmaticobjects.oo.data.anno.Scalar"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ScalarProcessor extends AbstractProcessor<Scalar> {
    public ScalarProcessor() {
        super(
            Scalar.class,
            (decl, mani, procEnv) -> List.of(new SrcFileScalar(decl, procEnv))
        );
    }
}
