package com.kkoz.lexemes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum End implements Lexeme {
    END(';');

    private final char text;

    public static Boolean contains(char code) {
        return END.text == code;
    }

    @Override
    public String getTypeName() {
        return "Конец строки";
    }
}
