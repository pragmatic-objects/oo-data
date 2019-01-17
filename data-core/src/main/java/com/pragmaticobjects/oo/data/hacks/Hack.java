/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.hacks;

import java.util.function.Supplier;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 *
 * @author skapral
 */
public class Hack {
    private Hack() {}
    
    public static TypeMirror extractType(Supplier<Class> supplier) {
        try {
            supplier.get();
            throw new RuntimeException(
                "If you see this exception, something in this cursed "
                + "world changed to better side and it is now "
                + "possible to extract "
                + "a class value from annotation. "
                + "Tell the developer to rewrite this ugly hack."
            );
        } catch(MirroredTypeException ex) {
            return ex.getTypeMirror();
        }
    }
}
