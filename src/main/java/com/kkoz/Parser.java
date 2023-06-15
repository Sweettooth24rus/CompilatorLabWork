package com.kkoz;

import com.kkoz.TokenTypes.Types;
import com.kkoz.Tree.BinaryOperator;
import com.kkoz.Tree.Condition;
import com.kkoz.Tree.Node;
import com.kkoz.Tree.UnaryOperator;

import java.util.ArrayList;

class Parser {

    // Текущий токен
    private Token currentToken;
    // объект лексера
    private com.kkoz.Lexer lexer;

    // Констркутор
    public Parser(com.kkoz.Lexer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    // Вывод ошибки
    private void dropError(String wanted, String received) {
        System.err.println("Неверный синтаксис\n" + "Хотел " + wanted + ", получил " + received);
        System.exit(-1);
    }

    // Метод вызывает ошибку, если текущмй токен не подходит под лексические правила
    // К примеру, все строки должны заканчиваться на ;
    // Если это не так, вызывается ошибка
    private void want(TokenTypes.Types tokenType) {
        if (currentToken.type == tokenType)
            currentToken = lexer.getNextToken();
        else
            dropError(tokenType.name(), currentToken.type.name());
    }

    // Фигурные скобки
    private Node compound_statement() {
        want(Types.LBRACE);
        Node[] nodes = statement_list();
        want(Types.RBRACE);

        com.kkoz.Tree.Compound root = new com.kkoz.Tree.Compound();
        for (Node node : nodes) {
            root.add(node);
        }
        return root;
    }

    // Вложенные выражения в фигурные скобки
    private Node[] statement_list() {
        ArrayList<Node> result = new ArrayList<Node>();
        result.add(statement());
        while (currentToken.type == Types.SEMI) {
            want(Types.SEMI);
            result.add(statement());
        }
        return result.toArray(new Node[0]);
    }

    // Всевозможные зарезервированные слова
    // В зависимости от типа токена вызываетися определенный метод
    private Node statement() {
        switch (currentToken.type) {
            case LBRACE:
                return compound_statement();
            case ID:
                return assignment_statement();
            case IF:
                return if_statement();
            case WHILE:
                return while_statement();
            case PRINT:
                return print_statement();
            case SCAN:
                return scan_statement();
            default:
                return new Node();
        }
    }

    // Разбор метода вывода в консоль
    private Node print_statement() {
        want(Types.PRINT);
        want(Types.LPAREN);
        Node v = variable();
        want(Types.RPAREN);
        return new com.kkoz.Tree.Print(v);
    }

    // разбор метода считываения из сконсоли
    private Node scan_statement() {
        want(Types.SCAN);
        want(Types.LPAREN);
        Node v = variable();
        want(Types.RPAREN);
        return new com.kkoz.Tree.Scan(v);
    }

    // Разбор условий
    private Node if_statement() {
        want(Types.IF);
        want(Types.LPAREN);
        Node c = expr();
        want(Types.RPAREN);
        return new Condition(c, compound_statement());
    }

    // Разбор циклов
    private Node while_statement() {
        want(Types.WHILE);
        want(Types.LPAREN);
        Node c = expr();
        want(Types.RPAREN);
        return new com.kkoz.Tree.Loop(c, compound_statement());
    }

    // Разбор присваивания
    private Node assignment_statement() {
        Node left = variable();
        Token token = currentToken;
        want(Types.ASSIGN);
        return new com.kkoz.Tree.Assign(left, token, expr());
    }

    // Переменные
    private Node variable() {
        com.kkoz.Tree.Var node = new com.kkoz.Tree.Var(currentToken);
        want(Types.ID);
        return node;
    }

    // Разбор выражений
    // Метод в зависимости от типа токена вызывает метод, который умеет его
    // обрабатывать
    private Node factor() {
        Token token = currentToken;
        switch (token.type) {
            case PLUS:
            case MINUS:
                want(token.type);
                return new UnaryOperator(token, factor());
            case INT:
                want(token.type);
                return new com.kkoz.Tree.Num(token);
            case LPAREN:
                want(token.type);
                Node node = expr();
                want(Types.RPAREN);
                return node;
            default:
                return variable();
        }
    }

    // Умножение, деление и сравнение переменных
    private Node term() {
        Node node = factor();
        while (currentToken.type == Types.DIV || currentToken.type == Types.MUL || currentToken.type == Types.LESS
            || currentToken.type == Types.MORE) {
            Token token = currentToken;
            want(token.type);
            node = new BinaryOperator(node, token, factor());
        }
        return node;
    }

    // Сложение и вычитание переменных
    // Так же совершение логических опреаторов
    private Node expr() {
        Node node = term();
        while (currentToken.type == Types.PLUS || currentToken.type == Types.MINUS) {
            Token token = currentToken;
            want(token.type);
            node = new BinaryOperator(node, token, term());
        }
        return node;
    }

    // Метод постраения синтаксического дерева
    Node parse() {
        Node[] nodes = statement_list();
        com.kkoz.Tree.Compound root = new com.kkoz.Tree.Compound();
        for (Node node : nodes) {
            root.add(node);
        }
        if (currentToken.type != Types.EOF)
            dropError(Types.EOF.name(), currentToken.type.name());
        return root;
    }
}