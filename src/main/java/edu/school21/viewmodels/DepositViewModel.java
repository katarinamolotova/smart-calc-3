package edu.school21.viewmodels;

import edu.school21.enums.PeriodType;
import edu.school21.enums.TermType;
import edu.school21.model.DepositCalcModel;
import edu.school21.viewmodels.handlers.Settings;
import edu.school21.viewmodels.handlers.WindowManager;
import edu.school21.viewmodels.helpers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public TextArea withdrawalsTextArea;
    public TextArea additionsTextArea;
    public ComboBox<String> capitalizationPeriod;
    public ComboBox<String> periodPayment;
    public TextField resultPercentTextField;
    public TextField summaTextField;
    public TextField taxSummaTextField;

    private DepositCalcModel depositCalcModel;

    private final List<LocalDate> additionsDate = new ArrayList<>();
    private final Map<LocalDate, Double> additionsList = new TreeMap<>();

    private final List<LocalDate> withdrawalsDate = new ArrayList<>();
    private final Map<LocalDate, Double> withdrawalsList = new TreeMap<>();

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
                .addListener(Validator.createListenerForNumberBetween(taxPercentTextField, 0, Validator.MAX_PERCENT));
        depositPercentTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(depositPercentTextField, 0, Validator.MAX_PERCENT));
        depositAmountTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(depositAmountTextField, 0, Validator.MAX_AMOUNT_MONEY));
        addAmountTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(addAmountTextField, 0, Validator.MAX_AMOUNT_MONEY));
        subAmountTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(subAmountTextField, 0, Validator.MAX_AMOUNT_MONEY));
        depositPeriodTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(depositPeriodTextField, Validator.MAX_AMOUNT_MONTHS));
        updateStyle();
    }

    @FXML
    public void clickCalculateButton() {
        depositCalcModel = new DepositCalcModel();
        final double resultPercent = getResultPercent();
        final double tax = depositCalcModel.sumTax(Double.parseDouble(taxPercentTextField.getText()), resultPercent);
        final double summa = depositCalcModel.sumAtTheEnd(Double.parseDouble(
                depositAmountTextField.getText()),
                resultPercent,
                additionsList,
                withdrawalsList);
        resultPercentTextField.setText(String.valueOf(resultPercent));
        taxSummaTextField.setText(String.valueOf(tax));
        summaTextField.setText(String.valueOf(summa));
    }

    private double getResultPercent() {
        return depositCalcModel.resultPercent(
                Double.parseDouble(depositAmountTextField.getText()),
                Integer.parseInt(depositPeriodTextField.getText()),
                getPeriodType(),
                Double.parseDouble(depositPercentTextField.getText()),
                getCapitalizationPeriod(),
                getPeriodPayment(),
                openDatePicker.getValue().getMonthValue(),
                additionsList,
                withdrawalsList
        );
    }

    private TermType getPeriodType() {
        final String item = periodComboBox.getSelectionModel().getSelectedItem();
        return item == null ? TermType.MONTH : TermType.getTermType(item);
    }

    private PeriodType getCapitalizationPeriod() {
        final String item = capitalizationPeriod.getSelectionModel().getSelectedItem();
        return item == null ? PeriodType.NONE : PeriodType.getPeriodType(item);
    }

    private PeriodType getPeriodPayment() {
        final String item = periodPayment.getSelectionModel().getSelectedItem();
        return item == null ? PeriodType.ONCE : PeriodType.getPeriodType(item);
    }

    @FXML
    public void addToAdditionsList() {
        addToList(additionsDate, additionsList, additionsTextArea, addAmountTextField, addDatePicker);
    }

    @FXML
    public void deleteFromAdditionsList() {
        deleteFromList(additionsDate, additionsList, additionsTextArea);
    }

    @FXML
    public void addToWithdrawalsList() {
        addToList(withdrawalsDate, withdrawalsList, withdrawalsTextArea, subAmountTextField, subDatePicker);

    }

    @FXML
    public void deleteFromWithdrawalsList() {
        deleteFromList(withdrawalsDate, withdrawalsList, withdrawalsTextArea);
    }

    private void addToList(
            final List<LocalDate> dates,
            final Map<LocalDate, Double> list,
            final TextArea textArea,
            final TextField textField,
            final DatePicker datePicker
    ) {
        final LocalDate additionDate = datePicker.getValue();
        if (list.containsKey(additionDate)) {
            WindowManager.showErrorMessage("Operation on this day already happened");
        } else {
            final Double sum = Double.parseDouble(textField.getText());
            list.put(additionDate, sum);
            dates.add(additionDate);
            updateTextArea(list, textArea);
        }
    }


    private void deleteFromList(
            final List<LocalDate> dates,
            final Map<LocalDate, Double> list,
            final TextArea textArea
    ) {
        final int lastIndex = dates.size() - 1;
        if (lastIndex >= 0) {
            list.remove(dates.get(lastIndex));
            dates.remove(lastIndex);
            updateTextArea(list, textArea);
        }
    }

    private void updateTextArea(final Map<LocalDate, Double> list, final TextArea textArea) {
        textArea.clear();
        list.forEach(
                (date, sum) -> {
                    final String text = textArea.getText();
                    textArea.setText(text + date + " - " + sum + "руб.\n");
                }
        );
    }
}
