package com.kkoz.exceptions;

public class SyntaxException extends Exception {
    public SyntaxException(char symbol, int i) {
        super(String.format("Неизвестный символ \"%s\". Позиция: %d", symbol, i));
    }
}
