package com.kkoz.lexemes;

import com.kkoz.SyntaxException;

import java.util.List;

public class LexemeService {
    public List<Lexeme> splitIntoLexemes(char[] chars, int i, List<Lexeme> result) throws SyntaxException {
        if (i >= chars.length) {
            return result;
        } else if (chars[i] == ' ' || chars[i] == '\n' || chars[i] == '\t') {
            splitIntoLexemes(chars, ++i, result);
        } else if (Character.isLetter(chars[i])) {
            getWord(chars, i, result);
        } else if (Character.isDigit(chars[i])) {
            getDigit(chars, i, result);
        } else {
            getSpecialSymbol(chars, i, result);
        }
        return result;
    }

    private void getWord(char[] chars, int i, List<Lexeme> result) throws SyntaxException {
        var wordBuilder = new StringBuilder();
        while (i < chars[i] && Character.isLetter(chars[i])) {
            wordBuilder.append(chars[i++]);
        }
        var word = wordBuilder.toString();

        if (Conditions.contains(word)) {
            result.add(Conditions.getByText(word));
        } else {
            result.add(new Variable(word));
        }

        splitIntoLexemes(chars, i, result);
    }

    private void getDigit(char[] chars, int i, List<Lexeme> result) throws SyntaxException {
        var word = new StringBuilder();
        while (i < chars[i] && (Character.isDigit(chars[i]) || chars[i] == ',')) {
            word.append(chars[i++]);
        }
        result.add(new FloatLexeme(word.toString()));

        var variableIndex = result.size() - 3;
        if (variableIndex < 0) {
            throw new SyntaxException(new FloatLexeme(word.toString()));
        }
        var variable = result.get(variableIndex);

        if (!variable.getClass().equals(Variable.class)) {
            throw new SyntaxException(new FloatLexeme(word.toString()));
        }
        ((Variable) variable).setValue(word.toString());

        splitIntoLexemes(chars, i, result);
    }

    private void getSpecialSymbol(char[] chars, int i, List<Lexeme> result) throws SyntaxException {
        if ((i < chars.length - 1) && Assign.contains(chars[i]) && Assign.contains(chars[i + 1])) {
            result.add(CompareOperators.EQUALS);
            splitIntoLexemes(chars, i + 2, result);
        } else if (CompareOperators.contains(chars[i])) {
            result.add(CompareOperators.getByText(chars[i]));
            splitIntoLexemes(chars, ++i, result);
        } else if (Assign.contains(chars[i])) {
            result.add(Assign.ASSIGN);
            splitIntoLexemes(chars, ++i, result);
        } else if (CurlyBraces.contains(chars[i])) {
            result.add(CurlyBraces.getByText(chars[i]));
            splitIntoLexemes(chars, ++i, result);
        } else if (RoundBraces.contains(chars[i])) {
            result.add(RoundBraces.getByText(chars[i]));
            splitIntoLexemes(chars, ++i, result);
        } else if (End.contains(chars[i])) {
            result.add(End.END);
            splitIntoLexemes(chars, ++i, result);
        }
    }
}
