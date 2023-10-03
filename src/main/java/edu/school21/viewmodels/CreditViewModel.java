package edu.school21.viewmodels;

import edu.school21.enums.CreditType;
import edu.school21.enums.TermType;
import edu.school21.model.CreditCalcModel;
import edu.school21.viewmodels.handlers.Settings;
import edu.school21.viewmodels.helpers.Validator;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class CreditViewModel implements ChildViewModel {

    public TextField commonSumTextField;
    public TextField periodTextField;
    public TextField percentTextField;
    public AnchorPane anchorPane;
    public ToggleGroup creditCalcType;
    public ComboBox<String> period;
    public TextArea monthlyPay;
    public TextField overPay;
    public TextField totalPayment;

    private CreditCalcModel creditCalcModel;

    @Override
    public void updateStyle() {
        anchorPane.getStylesheets().clear();
        anchorPane.getStylesheets().add("file:///" + Settings.STYLESHEET_FILE.getAbsolutePath().replace("\\", "/"));
    }

    @Override
    public void initialize() {
        creditCalcModel = new CreditCalcModel();

        commonSumTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(commonSumTextField, 0,  Validator.MAX_AMOUNT_MONEY));
        percentTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(percentTextField, 0, Validator.MAX_PERCENT));
        periodTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(periodTextField, Validator.MAX_AMOUNT_MONTHS));
        updateStyle();
    }

    public void clickCalculateButton() {
        creditCalcModel.calculate(
                getCreditType(),
                Double.parseDouble(commonSumTextField.getText()),
                Integer.parseInt(periodTextField.getText()),
                getPeriodType(),
                Double.parseDouble(percentTextField.getText())
        );
        monthlyPay.clear();
        overPay.setText(String.valueOf(creditCalcModel.getOverpay()));
        totalPayment.setText(String.valueOf(creditCalcModel.getTotalPayment()));

        final ArrayList<Double> monthlyPayment = creditCalcModel.getEveryMothPay();

        for (int i = 0; i < monthlyPayment.size(); i++) {
            final String text = monthlyPay.getText();
            monthlyPay.setText(text + "Месяц " + (i + 1) + ": " + monthlyPayment.get(i) + "руб.\n");
        }
    }

    private CreditType getCreditType() {
        final RadioButton creditType = (RadioButton) creditCalcType.getSelectedToggle();
        return CreditType.getCreditType(creditType.getText());
    }

    private TermType getPeriodType() {
        final String item = period.getSelectionModel().getSelectedItem();
        return item == null ? TermType.MONTH : TermType.getTermType(item);
    }
}
