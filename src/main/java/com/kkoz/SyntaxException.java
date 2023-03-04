package com.kkoz;

import com.kkoz.lexemes.Lexeme;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SyntaxException extends Exception {
    private final Lexeme lexeme;
}
