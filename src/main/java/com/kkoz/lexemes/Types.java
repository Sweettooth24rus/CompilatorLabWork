package com.kkoz.lexemes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Types implements Lexeme {
    INTEGER("int"),
    FLOAT("float");

    private final String text;

    private static final List<String> valueList = Arrays.stream(values())
        .map(Types::getText)
        .toList();

    public static Boolean contains(String code) {
        return valueList.contains(code);
    }

    public static Types getByText(String code) {
        for (var value : values()) {
            if (value.text.equals(code)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getTypeName() {
        return String.format("Тип \"%s\"", text);
    }
}
