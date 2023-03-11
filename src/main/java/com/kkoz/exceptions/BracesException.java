package com.kkoz.exceptions;

import com.kkoz.Token;

public class BracesException extends Exception {
    public BracesException(Token token) {
        super(String.format("%s. Непарная скобка. Позиция: %d", token.getTypeName(), token.getIndex()));
    }
}
