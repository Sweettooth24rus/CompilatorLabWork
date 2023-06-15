package com.kkoz.Tree;

import com.kkoz.Token;

// Унарный опреатор
public class UnaryOperator extends Node {
    // Эллемент, над которым совершается действие
    public Node right;

    // Конструктор
    public UnaryOperator(Token token, Node right) {
        super(token);
        this.right = right;
    }
}