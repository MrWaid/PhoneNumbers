package main.java;

import java.util.Objects;

public class Tree {

    public static class Node {

        Node[] _next = null;
        boolean _end = false;
        String _answer = "";

        public Node() {}

        public void Add(String current, String answer) {

            if (current.length() == 0) {
                _end = true;
                _answer = answer;
            }
            else {
                if (_next == null) {

                    _next = new Node[10];
                    for (int i = 0; i < 10; ++i) {

                        _next[i] = new Node();
                    }
                }

                _next[current.charAt(0) - '0'].Add(current.substring(1), answer);
            }
        }

        public void Add(String from, String to, String answer) {

            if (Objects.equals(from, to)) {
                Add(from, answer);
                return;
            }
            if (from.compareTo(to) > 0) {
                return;
            }

            if (_next == null) {

                _next = new Node[10];
                for (int i = 0; i < 10; ++i) {

                    _next[i] = new Node();
                }
            }
            if (from.charAt(0) == to.charAt(0)) {

                _next[from.charAt(0) - '0'].Add(from.substring(1), to.substring(1), answer);
            }
            else {

                for (int i = from.charAt(0) - '0' + 1; i < to.charAt(0) - '0'; ++i) {
                    _next[i]._end = true;
                    _next[i]._answer = answer;
                }

                StringBuilder min = new StringBuilder();
                StringBuilder max = new StringBuilder();

                for (int i = 0; i < from.length() - 1; ++i) {

                    min.append('9');
                    max.append('0');
                }
                _next[from.charAt(0) - '0'].Add(from.substring(1), min.toString(), answer);
                _next[to.charAt(0)   - '0'].Add(max.toString(), to.substring(1), answer);
            }
        }

        public String Find(String current) {
            if (_end) {
                return _answer;
            }

            if (_next != null) {
                return _next[current.charAt(0) - '0'].Find(current.substring(1));
            }
            else {
                return "Данного телефона нет в таблице.";
            }
        }
    }

    public static String CheckNumber(String number) {

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
}
