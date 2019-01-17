/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.model.manifest;

import com.pragmaticobjects.oo.data.model.declaration.Declaration;
import java.lang.annotation.Annotation;

/**
 *
 * @author skapral
 */
public interface Manifest {
    <A extends Annotation> Iterable<Declaration<A>> declarations(Class<A> type);
}
