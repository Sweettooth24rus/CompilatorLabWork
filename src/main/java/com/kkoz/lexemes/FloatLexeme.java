package com.kkoz.lexemes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FloatLexeme implements Lexeme {
    private final String text;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getTypeName() {
        return "Число";
    }

    @Override
    public String getValue() {
        return text;
    }
}
