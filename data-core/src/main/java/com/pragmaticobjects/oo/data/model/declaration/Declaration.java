/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.model.declaration;

import java.lang.annotation.Annotation;

/**
 *
 * @author skapral
 */
public interface Declaration<A extends Annotation> {
    A annotation();
    String packageName();
}
