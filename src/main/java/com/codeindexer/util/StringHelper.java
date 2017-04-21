package com.codeindexer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class of String functions
 */
public class StringHelper {

    private static final char SPACE = Character.valueOf(' ');
    private static final char DOT = Character.valueOf('.');

    /**
     * Removes characters which need not be indexed
     * @param line from which the chars needs to be removed
     * @return String which is cleaned up
     */
    public static String removeUnwantedCharacters(String line) {
        StringBuilder stringBuilder = new StringBuilder(line);

        for(int i = 0; i < stringBuilder.length(); i++) {
            if(!isValidCharacter(stringBuilder.charAt(i)) &&
                    stringBuilder.charAt(i) != SPACE) {
                stringBuilder.setCharAt(i, SPACE);
                continue;
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Checks if given character is a valid one
     * based on the contraints of the language
     * @param c the char whose validity needs to be tested
     * @return true if input char is valid
     *          false if input char is not valid
     */
    public static boolean isValidCharacter(char c) {
        if(c >= 65 && c <= 90)
            return true;

        if(c >= 97 && c <= 122)
            return true;

        if(c >= 48 && c <= 57)
            return true;

        //allowing _
        if(c == 95)
            return true;

        return false;
    }

    /**
     * gets first word from string
     * @param input the string from which
     * @return
     */
    public static String getFirstWord(String input) {
        if(input == null)
            return null;

        input = removeUnwantedCharacters(input);
        String[] strings = input.split(" ");
        return strings[0];
    }

    /**
     * removes unwanted characters from a string
     * and then splits it by space
     * @param input the string to be cleaned up
     * @return an array of string
     */
    public static String[] cleanupSpaceAndGetWords(String input) {
        if(input == null)
            return null;

        StringBuilder stringBuilder = new StringBuilder(input);

        for(int i = 0; i < stringBuilder.length(); i++) {
            if(!isValidCharacter(stringBuilder.charAt(i)) &&
                    stringBuilder.charAt(i) != SPACE &&
                    stringBuilder.charAt(i) != DOT) {
                stringBuilder.setCharAt(i, SPACE);
                continue;
            }
        }

        String[] strArray = stringBuilder.toString().split("\\s+");
        List<String> stringList = new ArrayList<>();

        for(String temp : strArray) {
            if(temp != null && !temp.isEmpty()) {
                stringList.add(temp);
            }
        }

        return stringList.toArray(new String[0]);
    }
}
