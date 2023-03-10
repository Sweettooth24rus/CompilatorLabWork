package com.kkoz.lexemes;

public interface Lexeme {
    String getText();

    String getTypeName();

    default String getValue() {
        return "";
    }

    default String getLexeme() {
        return String.format(
            "%s\t\t%s\t\t%s",
            getText(),
            getTypeName(),
            getValue()
        );
    }
}
