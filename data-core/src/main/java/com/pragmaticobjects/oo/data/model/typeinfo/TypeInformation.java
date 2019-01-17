package com.pragmaticobjects.oo.data.model.typeinfo;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

public interface TypeInformation {
    String name();
    Iterable<TypeName> superinterfaces();
    Iterable<FieldSpec> fields();
    Iterable<MethodSpec> methods();
}
