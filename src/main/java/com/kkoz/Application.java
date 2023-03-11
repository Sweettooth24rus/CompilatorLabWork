package com.kkoz;

import com.kkoz.exceptions.BracesException;
import com.kkoz.exceptions.SyntaxException;
import com.kkoz.lexemes.LexemeService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Application {
    public static void main(String[] args) throws SyntaxException, BracesException {
        var lexemeService = new LexemeService();
        var code = readFile();
        System.out.println();
        System.out.println(code);
        var tokens = lexemeService.splitByLexemes(code.toCharArray());
        for (var token : tokens) {
            System.out.println(token.getInfo());
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