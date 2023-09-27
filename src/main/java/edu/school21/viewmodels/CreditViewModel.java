package edu.school21.viewmodels;

import edu.school21.viewmodels.handlers.Settings;
import edu.school21.viewmodels.helpers.Validator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreditViewModel implements ChildViewModel {

    public TextField commonSumTextField;
    public TextField periodTextField;
    public TextField percentTextField;
    public AnchorPane anchorPane;

    @Override
    public void updateStyle() {
        anchorPane.getStylesheets().removeAll();
        anchorPane.getStylesheets().add(Settings.STYLESHEET_FILE_NAME);
    }

    @Override
    public void initialize() {
        commonSumTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(commonSumTextField, Validator.MAX_AMOUNT_MONEY));
        percentTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(percentTextField, Validator.MAX_PERCENT));
        periodTextField.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(periodTextField, Validator.MAX_AMOUNT_MONTHS));
        updateStyle();
    }

    public void clickCalculateButton() {
        //  вызов модели
    }
}
