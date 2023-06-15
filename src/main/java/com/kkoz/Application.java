package com.kkoz;

import java.io.File;
import java.util.Scanner;

// Главный класс программы
class Application {

    public static void main(String[] args) throws Exception {
        // Считывание файла из аргументов командой строки
        Scanner sc = new Scanner(new File("code.cwrk"));
        String code = "";
        while (sc.hasNextLine())
            code += sc.nextLine();
        sc.close();
        // Создание объекта лексара
        com.kkoz.Lexer lexer = new com.kkoz.Lexer(code);
        // Создание объекта парсера
        Parser parser = new Parser(lexer);
        // Создание объекта интерпретатора
        com.kkoz.Interpreter interpreter = new com.kkoz.Interpreter(parser);
        // Интерпретирование программы
        interpreter.interpret();
    }
}

