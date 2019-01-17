/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragmaticobjects.oo.data.exception;

/**
 *
 * @author skapral
 */
public class TupleGenerationException extends RuntimeException {
    public TupleGenerationException() {}

    public TupleGenerationException(String message) {
        super(message);
    }

    public TupleGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TupleGenerationException(Throwable cause) {
        super(cause);
    }

    public TupleGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
