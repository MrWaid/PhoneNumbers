package main.java;

import java.io.FileReader;
import au.com.bytecode.opencsv.CSVReader;
import java.util.Scanner;

import main.java.Tree.*;

public class CSVwork {

    public static void main(String[] args) throws Exception {

        Node nodes = new Node();

        Reader(nodes, "Data1.csv");
        Reader(nodes, "Data2.csv");
        Reader(nodes, "Data3.csv");
        Reader(nodes, "Data4.csv");

        System.out.println("Данные готовы к использованию, введите номер телефона.");

        Scanner in = new Scanner((System.in));
        String tmp = "";

        while (!tmp.equals("exit")) {

            tmp = in.nextLine();

            String number = Tree.CheckNumber(tmp);

            if (number != null) {
                System.out.println(nodes.Find(number));
            }
            else {
                if (!tmp.equals("exit")) {

                    System.out.println("Введён некорректный телефонный номер.");
                }
            }
        }
    }

    @SuppressWarnings("resource")
    public static void Reader(Node nodes, String file) throws Exception {
        String[] nextLine;

        CSVReader reader =
                new CSVReader(new FileReader("src/main/resources/" + file), ';' , '"' , 1);

        while ((nextLine = reader.readNext()) != null) {

            for (int i = 0; i < nextLine.length; ++i) {

                String from = nextLine[0] + nextLine[1];
                String to   = nextLine[0] + nextLine[2];
                String operator = nextLine[4];

                nodes.Add(from, to, operator);
            }
        }
    }
}