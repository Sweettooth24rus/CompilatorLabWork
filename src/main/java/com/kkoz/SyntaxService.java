package com.kkoz;

import com.kkoz.lexemes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SyntaxService {
    public String getTree(List<Token> tokens) {
        return writeIfBlock(tokens);
    }

    private String writeIfBlock(List<Token> tokens) {
        var result = "[ЕСЛИ ";
        var conditionBlock = new ArrayList<Token>();
        var flag = false;
        for (var i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getLexeme().equals(RoundBraces.START)) {
                flag = true;
                continue;
            }
            if (tokens.get(i).getLexeme().equals(RoundBraces.END)) {
                break;
            }
            if (flag) {
                conditionBlock.add(tokens.get(i));
            }
        }
        result += writeConditionBlock(conditionBlock) + " ";
        var expressionBlock = new ArrayList<Token>();
        flag = false;
        for (var i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getLexeme().equals(CurlyBraces.START)) {
                flag = true;
                continue;
            }
            if (tokens.get(i).getLexeme().equals(End.END)) {
                break;
            }
            if (flag) {
                expressionBlock.add(tokens.get(i));
            }
        }
        result += writeExpressionBlock(expressionBlock);
        return result + "]";
    }

    private String writeExpressionBlock(List<Token> tokens) {
        var result = "[";
        var assignIndex = findAssignIndex(tokens);
        result += tokens.get(assignIndex).getCode() + " ";
        result += "[" + writeBlock(tokens.get(assignIndex - 1)) + "] ";
        result += "[" + writeBlock(tokens.get(assignIndex + 1)) + "]";
        return result + "]";
    }

    private int findAssignIndex(List<Token> tokens) {
        for (var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);
            if (Arrays.stream(Assign.values()).anyMatch(o -> token.getLexeme().equals(o))) {
                return i;
            }
        }
        return 0;
    }

    private String writeConditionBlock(List<Token> tokens) {
        var result = "[";
        var compareOperatorIndex = findCompareOperatorIndex(tokens);
        result += tokens.get(compareOperatorIndex).getCode() + " ";
        result += "[" + writeBlock(tokens.get(compareOperatorIndex - 1)) + "] ";
        result += "[" + writeBlock(tokens.get(compareOperatorIndex + 1)) + "]";
        return result + "]";
    }

    private String writeBlock(Token token) {
        return token.getCode();
    }

    private int findCompareOperatorIndex(List<Token> tokens) {
        for (var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);
            if (Arrays.stream(CompareOperators.values()).anyMatch(o -> token.getLexeme().equals(o))) {
                return i;
            }
        }
        return 0;
    }
}
