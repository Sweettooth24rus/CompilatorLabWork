package com.kkoz;

import com.kkoz.lexemes.Lexeme;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Token<L extends Lexeme> {
    private final L lexeme;
    private final String code;
    private String value;
    private final Integer index;

    public Token(L lexeme, char code, Integer index) {
        this.lexeme = lexeme;
        this.code = String.valueOf(code);
        this.index = index;
    }

    public String getTypeName() {
        return lexeme.getTypeName();
    }

    public String getInfo() {
        return String.format(
            "%s\t\t%s\t\t%s",
            code,
            lexeme.getTypeName(),
            value
        );
    }

    public Boolean equalsByLexeme(Lexeme lexeme) {
        return lexeme.equals(this.lexeme);
    }
}
