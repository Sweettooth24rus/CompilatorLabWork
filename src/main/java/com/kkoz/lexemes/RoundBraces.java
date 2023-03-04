package com.kkoz.lexemes;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum RoundBraces implements Lexeme {
    START('('),
    END(')');

    private char text;

    private static final List<Character> valueList = Arrays.stream(values())
        .map(elem -> elem.text)
        .toList();

    public static Boolean contains(char code) {
        return valueList.contains(code);
    }

    public static RoundBraces getByText(char code) {
        for (var value : values()) {
            if (value.text == code) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getText() {
        return String.valueOf(text);
    }

    @Override
    public String getTypeName() {
        return "Фигурные скобки";
    }
}
