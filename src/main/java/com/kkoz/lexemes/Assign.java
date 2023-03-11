package com.kkoz.lexemes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Assign implements Lexeme {
    ASSIGN("=");

    private final String text;

    public static Boolean contains(char code) {
        return ASSIGN.text.equals(String.valueOf(code));
    }

    @Override
    public String getTypeName() {
        return "Оператор присваивания";
    }
}
