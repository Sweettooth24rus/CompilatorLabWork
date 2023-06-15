package com.kkoz;

import com.kkoz.lexemes.Variable;

import java.util.List;
import java.util.stream.Collectors;

public class IdentifierService {
    public String[] getIdentifiers(List<Token> tokens) {
        var identifierArray = new String[10];
        tokens.stream()
            .filter(token -> token.getLexeme().getClass().equals(Variable.class))
            .map(Token::getCode)
            .collect(Collectors.toSet())
            .forEach(code -> addToIdentifierArray(identifierArray, code));

        return identifierArray;
    }

    private void addToIdentifierArray(String[] identifierArray, String code) {
        var identifierIndex = getIdentifierIndex(code);
        if (identifierArray[identifierIndex] == null) {
            identifierArray[identifierIndex] = code;
            return;
        }
        addToIdentifierArrayWithAdditional(identifierArray, code, identifierIndex);
    }

    private void addToIdentifierArrayWithAdditional(String[] identifierArray, String code, int identifierIndex) {
        identifierIndex += getAdditionalIdentifierIndex(code);
        identifierIndex %= 10;
        if (identifierArray[identifierIndex] == null) {
            identifierArray[identifierIndex] = code;
            return;
        }
        addToIdentifierArrayWithAdditional(identifierArray, code, identifierIndex);
    }

    private int getIdentifierIndex(String code) {
        var codeValue = 0;
        for (var character : code.toCharArray()) {
            codeValue += character;
        }
        return codeValue % 10;
    }

    private int getAdditionalIdentifierIndex(String code) {
        var codeValue = 7;
        for (var character : code.toCharArray()) {
            codeValue += character;
        }
        return codeValue % 10;
    }
}
