package com.kkoz.lexemes;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum Conditions implements Lexeme {
    IF("if"),
    ELSE("else");

    private final String text;

    private static final List<String> valueList = Arrays.stream(values())
        .map(Conditions::getText)
        .toList();

    public static Boolean contains(String code) {
        return valueList.contains(code);
    }

    public static Conditions getByText(String code) {
        for (var value : values()) {
            if (value.text.equals(code)) {
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
        return "Условный оператор";
    }
}
