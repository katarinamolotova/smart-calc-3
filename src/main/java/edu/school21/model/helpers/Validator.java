package edu.school21.model.helpers;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Objects;

@AllArgsConstructor
public class Validator {
    public static void validateData(final String rawData) {
        LengthSizeCheck(rawData);
        BracketCountCheck(rawData);
        EmptyPlaceInBracketsCheck(rawData);
    }

    private static void LengthSizeCheck(final String rawData) {
        if (rawData.length() >= 255)
            throw new IllegalArgumentException("Wrong answer size");
    }
    private static void BracketCountCheck(final String rawData) {
        if (rawData.chars().asDoubleStream().filter(s -> s == '(').count()
                != rawData.chars().asDoubleStream().filter(s -> s == ')').count())
            throw new IllegalArgumentException("Branches");
    }

    private static void EmptyPlaceInBracketsCheck(final String rawData) {
        for (int i = 0; i < rawData.length(); i++) {
            if (rawData.charAt(i) == '(' && rawData.charAt(i+1) == ')') {
                throw new IllegalArgumentException("Empty branch");
            }
        }
    }

    public static void checkForDeposit(ArrayList<Pair<String, Pair<Integer, Double>>> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (Objects.equals(list.get(j).getKey(), list.get(i).getKey()) &&
                    Objects.equals(list.get(j).getValue().getKey(), list.get(i).getValue().getKey()))
                    throw new IllegalArgumentException("Error: Two additions in one month");
            }
        }
    }

//    private static void checkArithmeticSignConsistency (final String rawData) {
//        for (int i = 0; i < rawData.length(); i++) {
//            if (Character.isDigit(rawData.charAt(i))  && Character.isDigit(rawData.charAt(i+1)) {
//                throw new IllegalArgumentException("Wrong argument");
//            }
//        }
//    }
    
}
