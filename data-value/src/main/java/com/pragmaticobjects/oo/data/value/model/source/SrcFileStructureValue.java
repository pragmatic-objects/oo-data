package com.pragmaticobjects.oo.data.value.model.source;

import com.pragmaticobjects.oo.data.anno.Scalar;
import com.pragmaticobjects.oo.data.anno.Structure;
import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import com.pragmaticobjects.oo.data.model.manifest.Manifest;
import com.pragmaticobjects.oo.data.model.manifest.ManifestIndexSimple;
import com.pragmaticobjects.oo.data.model.source.javapoet.Destination;
import com.pragmaticobjects.oo.data.model.source.javapoet.SrcFileJavaPoet;
import com.pragmaticobjects.oo.data.model.source.javapoet.hacks.Hack;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.vavr.collection.List;
import javax.lang.model.element.Modifier;
import org.apache.commons.text.WordUtils;

/**
 * Value class source file for {@link Structure} declaration
 * @author skapral
 */
public class SrcFileStructureValue extends SrcFileJavaPoet {
    /**
     * Ctor.
     * @param declaration Declaration
     * @param dest Destination
     * @param manifest Manifest
     */
    public SrcFileStructureValue(Declaration<Structure> declaration, Destination dest, Manifest manifest) {
        super(
            declaration,
            () -> {
                final ManifestIndexSimple<String, Scalar> manifestIndex = new ManifestIndexSimple<>(manifest, Scalar.class, Scalar::value);
                final String name = declaration.annotation().value() + "Value";
                final TypeName type = ClassName.bestGuess(declaration.annotation().value());
                final List<ClassName> superifaces = List.of(declaration.annotation().has())
                        .map(has -> 
                            ClassName.get(
                                declaration.packageName(), has
                            )
                        );
                final List<FieldSpec> fields = List.of(declaration.annotation().has())
                    .map(superinterface -> {
                        boolean multiple = false;
                        if(superinterface.startsWith("*")) {
                            multiple = true;
                            superinterface = superinterface.replace("*", "");
                        }
                        final Declaration<Scalar> scalar = manifestIndex.uniqueByKey(superinterface);
                        final String fieldName = WordUtils.uncapitalize(superinterface);
                        final TypeName fieldType = Hack.extractType(scalar.annotation()::type);
                        return FieldSpec.builder(
                                fieldType, fieldName,
                                Modifier.PRIVATE, Modifier.FINAL
                        ).build();
                    });
                
                final MethodSpec constructor = fields.foldLeft(
                    MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC),
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
                        .addSuperinterface(type)
                        .addFields(fields)
                        .addMethod(constructor)
                        .addMethods(accessors)
                        .build();
            },
            dest
        );
    }
}
