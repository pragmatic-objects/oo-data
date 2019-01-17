/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.value.model.typeinfo;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.hacks.Hack;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.typeinfo.TypeInformation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.vavr.collection.List;
import javax.lang.model.element.Modifier;
import org.apache.commons.text.WordUtils;

/**
 *
 * @author skapral
 */
public class ScalarTypeInformation implements TypeInformation {
    private final Declaration<Scalar> declaration;

    public ScalarTypeInformation(Declaration<Scalar> declaration) {
        this.declaration = declaration;
    }

    @Override
    public final String name() {
        return declaration.annotation().value() + "Value";
    }

    @Override
    public final Iterable<TypeName> superinterfaces() {
        return List.of(
            ClassName.get(
                declaration.packageName(), declaration.annotation().value()
            )
        );
    }

    @Override
    public final Iterable<FieldSpec> fields() {
        return List.of(
            FieldSpec.builder(
                TypeName.get(Hack.extractType(declaration.annotation()::type)),
                WordUtils.uncapitalize(declaration.annotation().value()),
                Modifier.PRIVATE, Modifier.FINAL
            ).build()
        );
    }

    @Override
    public final Iterable<MethodSpec> methods() {
        final Iterable<FieldSpec> fields = fields();
        MethodSpec.Builder cbuilder = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC);
        for(FieldSpec f : fields) {
            cbuilder = cbuilder
                .addParameter(f.type, f.name)
                .addStatement("this.$L = $L", f.name, f.name);
        }
        final List<MethodSpec> accessors = List.ofAll(fields)
            .map(f -> MethodSpec.methodBuilder(f.name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .returns(f.type)
                .addCode("return this.$L;", f.name)
                .build());
        return accessors.append(
            cbuilder.build()
        );
    }
}
