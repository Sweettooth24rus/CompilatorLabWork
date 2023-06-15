package com.kkoz.Tree;

import com.kkoz.Token;

// Класс отвечающий за равно
public class Assign extends Node {
    // Элементы слева и справа от равно
    public Node left, right;

    // Конструктор
    public Assign(Node left, Token token, Node right) {
        super(token);
        this.left = left;
        this.right = right;
    }
}