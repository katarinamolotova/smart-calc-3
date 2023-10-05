package edu.school21.viewmodels.helpers;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;


public class Validator {
    public static final Integer MAX_AMOUNT_MONEY = 10000000;
    public static final Integer MAX_FOR_GRAPH = 1000000;
    public static final Integer MIN_FOR_GRAPH = -1000000;
    public static final Integer MAX_AMOUNT_MONTHS = 720;
    public static final Integer MAX_PERCENT = 100;
    public static final Integer MAX_LENGTH = 255;

    /**
     * Слушатель для ввода только положительных чисел в диапазоне от 0 до max
     */
    public static ChangeListener<String> createListenerForPositiveNumberWithLimit(final TextField textField, final Integer max) {
        return (observable, oldValue, newValue) -> {
            if (checkIsPositiveNumber(newValue) && checkNumberLessOrEqualsThen(newValue, max)) {
                return;
            }
            textField.setText(oldValue);
        };
    }

    /**
     * Слушатель для ввода только чисел (в том числе отрицательных) в диапазоне от min до max
     */
    public static ChangeListener<String> createListenerForNumberBetween(final TextField textField,
                                                                        final Integer min,
                                                                        final Integer max) {
        return (observable, oldValue, newValue) -> {
            if (checkNumberBetween(newValue, min, max)) {
                return;
            }
            textField.setText(oldValue);
        };
    }

    /**
     * Слушатель для ограничения длины (max = 255)
     */
    public static ChangeListener<String> createListenerForLength(final TextArea textArea) {
        return (observable, oldValue, newValue) -> {
            if (checkLength(newValue)) {
                return;
            }
            textArea.setText(oldValue);
        };
    }

    /**
     * Слушатель для ограничения даты.
     * Датат не может быть меньше, чем сегодня
     */
    public static ChangeListener<LocalDate> createListenerForDate(final DatePicker datePicker) {
        return (observable, oldValue, newValue) -> {
            if (newValue.isBefore(LocalDate.now())) {
                datePicker.setValue(oldValue);
            }
        };
    }

    private static boolean checkLength(final String value) {
        return value.length() <= MAX_LENGTH;
    }

    private static boolean checkIsPositiveNumber(final String value) {
        return String.valueOf(value).matches("\\d*");
    }

    private static boolean checkNumberLessOrEqualsThen(final String value, final Integer max) {
        final int number = Integer.parseInt(value.isEmpty() ? "0" : value);
        return number <= max;
    }

     static boolean checkNumberBetween(final String value, final Integer min, final Integer max) {
        final double number;
        try {
            final String tempValue = (value.isEmpty() || value.equals("-")) ? "0" : value;
            number = Double.parseDouble(tempValue);
        } catch (final NumberFormatException e) {
            return false;
        }
        return number >= min && number <= max;
    }
}
