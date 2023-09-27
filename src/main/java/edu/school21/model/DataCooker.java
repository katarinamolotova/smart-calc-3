package edu.school21.model;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCooker {
    private static final String REGEX = "[-+]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][-+]?\\d+)";

    public static String DataCook(String rawData, double xValue) {
        String temp = replaceValueX(rawData, xValue);
        temp = exponentialEntryReplacement(temp);
        return insertMultiple(temp.replace('.', ','));
    }

    private static String insertMultiple(final String inputString) {
//        StringBuffer bf = new StringBuffer(inputString);
//        TO_DO
        return inputString;
    }

    private static String exponentialEntryReplacement(final String inputString) {
        String result = inputString;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()){
            String expValue = checkPlusAtBeginString(matcher.group(0));
            String value = BigDecimal.valueOf(Double.parseDouble(expValue)).toPlainString();
            result = result.replace(expValue, value);
        }
        return result;
    }

    private static String checkPlusAtBeginString(final String str) {
        return str.charAt(0) != '+' ? str : str.substring(1);
    }

    private static String replaceValueX(final String inputString, final double value) {
        return inputString.replace("x", "(" + value + ")");
    }

}
