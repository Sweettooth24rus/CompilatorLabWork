package com.kkoz;

import com.kkoz.lexemes.LexemeService;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Application {
    public static void main(String[] args) throws SyntaxException {
        var lexemeService = new LexemeService();
        var code = readFile();
        System.out.println();
        System.out.println(code);
        var lexemes = lexemeService.splitIntoLexemes(code.toCharArray(), 0, new ArrayList<>());
        for (var lexeme : lexemes) {
            System.out.println(lexeme.getLexeme());
        }
    }

    private static String readFile() {
        var result = new StringBuilder();
        try (var reader = new FileReader("code/test.txt")) {
            var buf = new char[256];
            int c;
            while ((c = reader.read(buf)) > 0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                result.append(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}