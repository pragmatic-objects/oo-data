/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.model.declaration;

import java.lang.annotation.Annotation;
import javax.lang.model.element.PackageElement;

/**
 *
 * @author skapral
 */
public class DeclExplicit<A extends Annotation> implements Declaration<A> {
    private final A annotation;
    private final String packageName;

    public DeclExplicit(A annotation, String packageName) {
        this.annotation = annotation;
        this.packageName = packageName;
    }
    
    @Override
    public final A annotation() {
        return annotation;
    }

    @Override
    public final String packageName() {
        return packageName;
    }
}
