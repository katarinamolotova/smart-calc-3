package edu.school21.viewmodels;

import edu.school21.viewmodels.handlers.Settings;
import edu.school21.viewmodels.helpers.Validator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class DepositViewModel implements ChildViewModel {

    public TextField depositAmountTextField;
    public TextField depositPeriodTextField;
    public TextField depositPercentTextField;
    public TextField taxPercentTextField;
    public TextField addAmountTextField;
    public TextField subAmountTextField;
    public ComboBox<String> periodComboBox;
    public DatePicker openDatePicker;
    public DatePicker addDatePicker;
    public DatePicker subDatePicker;
    public AnchorPane anchorPane;

    @Override
    public void updateStyle() {
        anchorPane.getStylesheets().removeAll();
        anchorPane.getStylesheets().add(Settings.STYLESHEET_FILE_NAME);
    }

    @Override
    public void initialize() {
        openDatePicker.setValue(LocalDate.now());
        addDatePicker.setValue(LocalDate.now());
        subDatePicker.setValue(LocalDate.now());

        openDatePicker.valueProperty().addListener(Validator.createListenerForDate(openDatePicker));
        addDatePicker.valueProperty().addListener(Validator.createListenerForDate(addDatePicker));
        subDatePicker.valueProperty().addListener(Validator.createListenerForDate(subDatePicker));
        taxPercentTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(taxPercentTextField, Validator.MAX_PERCENT));
        depositPercentTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(depositPercentTextField, Validator.MAX_PERCENT));
        depositAmountTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(depositAmountTextField, Validator.MAX_AMOUNT_MONEY));
        addAmountTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(addAmountTextField, Validator.MAX_AMOUNT_MONEY));
        subAmountTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(subAmountTextField, Validator.MAX_AMOUNT_MONEY));
        depositPeriodTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(depositPeriodTextField, Validator.MAX_AMOUNT_MONTHS));
        updateStyle();
    }

    public void clickCalculateButton() {
        // вызов модели
    }
}
