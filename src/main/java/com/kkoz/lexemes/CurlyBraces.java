package com.kkoz.lexemes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CurlyBraces implements Lexeme {
    START('{'),
    END('}');

    private final char text;

    private static final List<Character> valueList = Arrays.stream(values())
        .map(elem -> elem.text)
        .toList();

    public static Boolean contains(char code) {
        return valueList.contains(code);
    }

    public static CurlyBraces getByText(char code) {
        for (var value : values()) {
            if (value.text == code) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getTypeName() {
        return String.format("Фигурная скобка: \"%s\"", text);
    }
}
