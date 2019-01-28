package com.pragmaticobjects.oo.data.value.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.pragmaticobjects.oo.data.model.source.javapoet.hacks.Hack;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.vavr.collection.List;
import org.apache.commons.text.WordUtils;

import javax.lang.model.element.Modifier;

/**
 * Value class source file for {@link Scalar} declaration
 * 
 * @author skapral
 */
public class SrcFileScalarValue extends SrcFileJavaPoet {
    /**
     * Ctor.
     * @param declaration Declaration
     * @param dest Destination
     */
    public SrcFileScalarValue(Declaration<Scalar> declaration, Destination dest) {
        super(
            declaration,
            () -> {
                final String name = declaration.annotation().value() + "Value";
                final List<ClassName> superifaces = List.of(
                    ClassName.get(
                        declaration.packageName(), declaration.annotation().value()
                    )
                );
                final List<FieldSpec> fields = List.of(
                    FieldSpec.builder(
                        Hack.extractType(declaration.annotation()::type),
                        WordUtils.uncapitalize(declaration.annotation().value()),
                        Modifier.PRIVATE, Modifier.FINAL
                    ).build()
                );
                final MethodSpec constructor = fields.foldLeft(
                    MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC),
                    (cbuilder, f) -> cbuilder
                        .addParameter(f.type, f.name)
                        .addStatement("this.$L = $L", f.name, f.name)
                ).build();
                final List<MethodSpec> accessors = fields
                    .map(f -> MethodSpec.methodBuilder(f.name)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .returns(f.type)
                        .addCode("return this.$L;", f.name)
                        .build()
                    );
                return TypeSpec.classBuilder(name)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterfaces(superifaces)
                    .addFields(fields)
                    .addMethod(constructor)
                    .addMethods(accessors)
                    .build();
            },
            dest
        );
    }
}
