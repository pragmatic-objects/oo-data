/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.value.model.typeinfo;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.hacks.Hack;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.typeinfo.TypeInformation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.vavr.collection.List;
import org.apache.commons.text.WordUtils;

import javax.lang.model.element.Modifier;

/**
 *
 * @author skapral
 */
public class StructureTypeInformation implements TypeInformation {
    private final Declaration<Structure> declaration;

    public StructureTypeInformation(Declaration<Structure> declaration) {
        this.declaration = declaration;
    }

    @Override
    public final String name() {
        return declaration.annotation().value() + "Structure";
    }

    @Override
    public final Iterable<TypeName> superinterfaces() {
        final String pkgName = declaration.packageName();
        return List.of(declaration.annotation().has())
            .map(hasStmt -> ClassName.get(pkgName, hasStmt));
    }

    @Override
    public final Iterable<FieldSpec> fields() {
        final String pkgName = declaration.packageName();
        return List.of(declaration.annotation().has())
            .map(hasStmt -> FieldSpec.builder(
                ClassName.get(pkgName, hasStmt),
                WordUtils.uncapitalize(hasStmt),
                Modifier.PRIVATE, Modifier.FINAL
            ).build());
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
