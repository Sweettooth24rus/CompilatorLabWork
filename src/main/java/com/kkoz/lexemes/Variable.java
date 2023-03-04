package com.kkoz.lexemes;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Variable implements Lexeme {
    private final String text;
    @Setter
    private String value = "";

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getTypeName() {
        return "Переменная";
    }

    @Override
    public String getValue() {
        return value;
    }
}
