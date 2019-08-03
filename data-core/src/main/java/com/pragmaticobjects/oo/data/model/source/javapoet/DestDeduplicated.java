/*-
 * ===========================================================================
 * data-core
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 Kapralov Sergey
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ============================================================================
 */
package com.pragmaticobjects.oo.data.model.source.javapoet;

import com.pragmaticobjects.oo.data.anno.Import;
import com.squareup.javapoet.JavaFile;
import io.vavr.collection.List;
import javax.annotation.processing.ProcessingEnvironment;

/**
 * Destination, that checks incoming files for duplicate in other packages.
 * 
 * @author skapral
 */
public class DestDeduplicated implements Destination {
    private final List<Import> imports;
    private final ProcessingEnvironment procEnv;
    private final Destination delegate;

    /**
     * Ctor.
     * @param imports Imports
     * @param procEnv Processing environment
     * @param delegate Delegating destination
     */
    public DestDeduplicated(List<Import> imports, ProcessingEnvironment procEnv, Destination delegate) {
        this.imports = imports;
        this.procEnv = procEnv;
        this.delegate = delegate;
    }

    

    @Override
    public final void persist(JavaFile file) {
        String name = file.packageName + "." + file.typeSpec.name;
        if(procEnv.getElementUtils().getTypeElement(name) == null) {
            delegate.persist(file);
        }
    }
    
}
