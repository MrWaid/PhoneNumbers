package main.java;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class PhoneNumbers {

    public static void main(String[] args) throws Exception {

        HashMap<Long, String> array = new HashMap<>();

        reader(array, "Data1.csv");
        reader(array, "Data2.csv");
        reader(array, "Data3.csv");
        reader(array, "Data4.csv");

        System.out.println("Данные готовы к использованию, введите номер телефона.");

        Scanner in = new Scanner((System.in));
        String tmp = "";

        while (!tmp.equals("exit")) {

            tmp = in.nextLine();

            String number = checkNumber(tmp);

            if (number != null) {
                String answer = "Такого номера нет в базе данных.";

                for (int i = 1; i <= 10; ++i) {

                    String getKey = array.get(Long.parseLong(number.substring(0, i)));
                    if (getKey != null) {
                        answer = getKey;
                        break;
                    }
                }
                System.out.println(answer);
            }
            else {
                if (!tmp.equals("exit")) {
                    System.out.println("Введён некорректный телефонный номер.");
                }
            }
        }
    }

    @SuppressWarnings("resource")
    public static void reader(HashMap<Long, String> array, String file) throws Exception {
        String[] nextLine;

        CSVReader reader =
                new CSVReader(new FileReader("src/main/resources/" + file), ';', '/', 1);

        while ((nextLine = reader.readNext()) != null) {

            for (int i = 0; i < nextLine.length; ++i) {

                String from = nextLine[0] + nextLine[1];
                String to   = nextLine[0] + nextLine[2];
                String operator = nextLine[4];

                add(array, from, to, operator);
            }
        }
    }

    public static String checkNumber(String number) {

        if (number.length() == 10 && number.compareTo("0000000000"  ) >= 0 && number.compareTo("9999999999"  ) <= 0) {
            return number;
        }
        if (number.length() == 11 && number.compareTo("80000000000" ) >= 0 && number.compareTo("89999999999" ) <= 0) {
            return number.substring(1);
        }
        if (number.length() == 12 && number.compareTo("+70000000000") >= 0 && number.compareTo("+79999999999") <= 0) {
            return number.substring(2);
        }

        return null;
    }

    public static void add(HashMap<Long, String> array, String from, String to, String operator) {

        if (Objects.equals(from, to)) {
            array.put(Long.parseLong(from), operator);
            return;
        }

        if (from.compareTo(to) > 0) {
            return;
        }

        int length = 0;
        while (from.charAt(length) == to.charAt(length)) {
            ++length;
        }

        long commonPart = Long.parseLong(from.substring(0, length));

        for (int i = from.charAt(length) - '0' + 1; i < to.charAt(length) - '0'; ++i) {

            array.put(commonPart * 10 + i, operator);
        }

        long begin = commonPart * 10 + to.charAt(length) - '0';
        long end = commonPart * 10 + from.charAt(length) - '0';
        for (int i = 0; i < 10 - length - 1; ++i) {

            begin *= 10;
            end *= 10;
            end += 9;
        }
        add(array, from, Long.toString(end), operator);
        add(array, Long.toString(begin), to, operator);
    }
}
