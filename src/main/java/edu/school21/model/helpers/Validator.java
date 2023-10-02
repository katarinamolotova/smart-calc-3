package edu.school21.model.helpers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Validator {
    public static void validateData(final String rawData) {
        LengthSizeCheck(rawData);
        BracketCountCheck(rawData);
        EmptyPlaceInBracketsCheck(rawData);
    }

    private static void LengthSizeCheck(final String rawData) {
        if (rawData.length() >= 255) {
            throw new IllegalArgumentException("Wrong answer size");
        }
    }
    private static void BracketCountCheck(final String rawData) {
        if (rawData.chars().asDoubleStream().filter(s -> s == '(').count()
                != rawData.chars().asDoubleStream().filter(s -> s == ')').count()) {
            throw new IllegalArgumentException("Branches");
        }
    }

    private static void EmptyPlaceInBracketsCheck(final String rawData) {
        for (int i = 0; i < rawData.length(); i++) {
            if (rawData.charAt(i) == '(' && rawData.charAt(i+1) == ')') {
                throw new IllegalArgumentException("Empty branch");
            }
        }
    }
}
