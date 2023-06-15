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
        var identifierService = new IdentifierService();
        var syntaxService = new SyntaxService();

        System.out.println();

        var code = readFile();
        System.out.println(code);
        System.out.println();

        var tokens = lexemeService.splitByLexemes(code.toCharArray());
        for (var token : tokens) {
            System.out.println(token.getInfo());
        }
        System.out.println();

        var identifiers = identifierService.getIdentifiers(tokens);
        for (var i = 0; i < identifiers.length; i++) {
            System.out.println(i + "\t" + identifiers[i]);
        }
        System.out.println();

        System.out.println(syntaxService.getTree(tokens));
        System.out.println();
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