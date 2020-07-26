package by.itechart.logic.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class NameGeneratorUtil {

    private static Random random = new Random();
    private static final int NAME_LENGTH = 9;
    private static char[] letters = new char[26];

    public static final String DELIMITER = "_d_e_l_";

    static {
        for (int i = 'a', count = 0; i <= 'z'; i++, count++) {
            letters[count] = (char) i;
        }
    }

    public static String generate() {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < NAME_LENGTH; i++) {
            int randomIndex = random.nextInt(letters.length);
            result.append(letters[randomIndex]);
        }

        final String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        return result.toString() + date + DELIMITER;
    }

    public static String generate(String start) {
        return start + generate();
    }


}
