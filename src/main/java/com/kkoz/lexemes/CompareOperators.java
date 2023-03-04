package com.kkoz.lexemes;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum CompareOperators implements Lexeme {
    GREATER(">"),
    LESS("<"),
    EQUALS("==");

    private final String text;
    private static final List<String> valueList = Arrays.stream(values())
        .map(CompareOperators::getText)
        .toList();

    public static Boolean contains(char code) {
        return contains(String.valueOf(code));
    }

    public static Boolean contains(String code) {
        return valueList.contains(code);
    }

    public static CompareOperators getByText(char code) {
        var codeString = String.valueOf(code);
        for (var value : values()) {
            if (value.text.equals(codeString)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getTypeName() {
        return "Оператор сравнения";
    }
}
