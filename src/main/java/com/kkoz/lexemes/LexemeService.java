package com.kkoz.lexemes;

import com.kkoz.Token;
import com.kkoz.exceptions.BracesException;
import com.kkoz.exceptions.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class LexemeService {

    public List<Token> splitByLexemes(char[] chars) throws SyntaxException, BracesException {
        var tokenList = splitByLexemes(chars, 0, new ArrayList<>());

        checkCurlyBraces(tokenList);
        checkRoundBraces(tokenList);

        return tokenList;
    }

    private void checkCurlyBraces(List<Token> tokenList) throws BracesException {
        checkBraces(CurlyBraces.START, CurlyBraces.END, tokenList);
    }

    private void checkRoundBraces(List<Token> tokenList) throws BracesException {
        checkBraces(RoundBraces.START, RoundBraces.END, tokenList);
    }

    private void checkBraces(Lexeme leftBrace,
                             Lexeme rightBrace,
                             List<Token> tokenList) throws BracesException {
        var braces = tokenList.stream()
            .filter(elem -> elem.equalsByLexeme(leftBrace) || elem.equalsByLexeme(rightBrace))
            .toList();
        for (int i = 0; i < braces.size(); ) {
            var left = braces.get(i++);
            if (!left.equalsByLexeme(leftBrace)) {
                throw new BracesException(left);
            }
            var right = braces.get(i++);
            if (!right.equalsByLexeme(rightBrace)) {
                throw new BracesException(right);
            }
        }
    }

    public List<Token> splitByLexemes(char[] chars, int i, List<Token> result) throws SyntaxException {
        if (i >= chars.length) {
            return result;
        } else if (chars[i] == ' ' || chars[i] == '\n' || chars[i] == '\t') {
            splitByLexemes(chars, ++i, result);
        } else if (Character.isLetter(chars[i])) {
            getWord(chars, i, result);
        } else if (Character.isDigit(chars[i])) {
            getDigit(chars, i, result);
        } else {
            getSpecialSymbol(chars, i, result);
        }
        return result;
    }

    private void getWord(char[] chars, int i, List<Token> result) throws SyntaxException {
        var wordBuilder = new StringBuilder();
        while (i < chars[i] && Character.isLetter(chars[i])) {
            wordBuilder.append(chars[i++]);
        }
        var word = wordBuilder.toString();

        if (Conditions.contains(word)) {
            result.add(new Token<>(Conditions.getByText(word), word, i));
        } else {
            result.add(new Token<>(new Variable(), word, i));
        }

        splitByLexemes(chars, i, result);
    }

    private void getDigit(char[] chars, int i, List<Token> result) throws SyntaxException {
        var word = new StringBuilder();
        while (i < chars[i] && (Character.isDigit(chars[i]) || chars[i] == ',' || chars[i] == 'e')) {
            word.append(chars[i++]);
        }

        result.add(new Token<>(new FloatLexeme(), word.toString(), i));
        splitByLexemes(chars, i, result);
    }

    private void getSpecialSymbol(char[] chars, int i, List<Token> result) throws SyntaxException {
        if ((i < chars.length - 1) && Assign.contains(chars[i]) && Assign.contains(chars[i + 1])) {
            result.add(new Token<>(CompareOperators.EQUALS, CompareOperators.EQUALS.getText(), i));
            i++;
        } else if (CompareOperators.contains(chars[i])) {
            result.add(new Token<>(CompareOperators.getByText(chars[i]), chars[i], i));
        } else if (Assign.contains(chars[i])) {
            result.add(new Token<>(Assign.ASSIGN, chars[i], i));
        } else if (CurlyBraces.contains(chars[i])) {
            result.add(new Token<>(CurlyBraces.getByText(chars[i]), chars[i], i));
        } else if (RoundBraces.contains(chars[i])) {
            result.add(new Token<>(RoundBraces.getByText(chars[i]), chars[i], i));
        } else if (End.contains(chars[i])) {
            result.add(new Token<>(End.END, chars[i], i));
        } else {
            throw new SyntaxException(chars[i], i);
        }
        splitByLexemes(chars, ++i, result);
    }
}
