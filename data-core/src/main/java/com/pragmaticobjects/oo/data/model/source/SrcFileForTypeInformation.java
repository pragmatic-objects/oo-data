package com.pragmaticobjects.oo.data.model.source;

import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.typeinfo.TypeInformation;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;

public class SrcFileForTypeInformation extends SrcFileJavaPoet {
    public SrcFileForTypeInformation(TypeInformation info, Declaration<?> declaration, ProcessingEnvironment env) {
        super(
            declaration,
            () -> {
                TypeSpec.Builder builder = TypeSpec.classBuilder(info.name());
                for(FieldSpec field : info.fields()) {
                    builder = builder.addField(field);
                }
                for(MethodSpec method : info.methods()) {
                    builder = builder.addMethod(method);
                }
                return builder.build();
            },
            env
        );
    }
}
