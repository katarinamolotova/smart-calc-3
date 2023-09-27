package edu.school21.model;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class Validator {
    public static void validateData(final String rawData) {
        LengthSizeCheck(rawData);
        BracketCountCheck(rawData);
        EmptyPlaceInBracketsCheck(rawData);
    }

    public static void LengthSizeCheck(final String rawData) {
        if (rawData.length() >= 255)
            throw new IllegalArgumentException("Wrong answer size");
    }
    public static void BracketCountCheck(final String rawData) {
        if (rawData.chars().asDoubleStream().filter(s -> s == '(').count()
                != rawData.chars().asDoubleStream().filter(s -> s == ')').count())
            throw new IllegalArgumentException("Branches");
    }

    public static void EmptyPlaceInBracketsCheck(final String rawData) {
        // TO_DO
    }
    
}
