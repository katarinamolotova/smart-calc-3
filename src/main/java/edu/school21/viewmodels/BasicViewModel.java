package edu.school21.viewmodels;

import edu.school21.enums.RotationPeriod;
import edu.school21.enums.ScreenType;
import edu.school21.viewmodels.handlers.History;
import edu.school21.viewmodels.handlers.WindowManager;
import edu.school21.viewmodels.helpers.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BasicViewModel {
    public Label minLabel;
    public Label maxLabel;
    public TextField minTextField;
    public TextField maxTextField;
    public TextArea calcTextArea;
    public CheckBox graphCheckBox;
    public AnchorPane anchorPane;
    public ListView<String> listHistory;
    public RadioMenuItem minutelyRadioItem;
    public RadioMenuItem hourlyRadioItem;
    public RadioMenuItem dailyRadioItem;
    public RadioMenuItem monthlyRadioItem;
    public ToggleGroup period;

    private WindowManager windowManager;
    private History history;

    public void initialize(Stage stage) {
        history = new History(listHistory);
        history.loadHistoryFromFile();
        setRadioMenuItemRotationPeriodSelected(RotationPeriod.valueOf(history.getLoggerName()));

        windowManager = new WindowManager(anchorPane, stage);
        windowManager.updateStyle();

        minTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(minTextField, Validator.MIN_FOR_GRAPH, Validator.MAX_FOR_GRAPH));
        maxTextField.textProperty()
                .addListener(Validator.createListenerForNumberBetween(maxTextField, Validator.MIN_FOR_GRAPH, Validator.MAX_FOR_GRAPH));
        calcTextArea.textProperty().addListener(Validator.createListenerForLength(calcTextArea));
    }

    public void stop() {
        windowManager.saveSettings();
        history.saveHistoryToFile();
    }

    @FXML
    public void clickOnTextButton(ActionEvent event) {
        final Button button = (Button) event.getSource();
        final String text = calcTextArea.getText();
        calcTextArea.setText(text + button.getText());
    }

    @FXML
    public void clickOnClearAllButton() {
        calcTextArea.setText("");
    }

    @FXML
    public void clickOnClearOneSymbolButton() {
        final String text = calcTextArea.getText();
        final int endIndex = text.isEmpty() ? 0 : text.length() - 1;
        calcTextArea.setText(text.substring(0, endIndex));
    }

    @FXML
    public void clickOnEqualsButton() {
        history.addExpressionToHistory(calcTextArea.getText());
        //  вызов модели
    }

    @FXML
    public void chooseItemFromHistory(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            calcTextArea.setText(history.getSelectedItem());
        }
    }

    @FXML
    public void clearHistory() {
        history.clear();
    }

    @FXML
    public void changeLogRotationPeriod() {
        final RadioMenuItem radioMenuItem = (RadioMenuItem) period.getSelectedToggle();
        history.changeLoggerByRotationPeriod(RotationPeriod.valueOf(radioMenuItem.getText().toUpperCase()));
    }
    
    private void setRadioMenuItemRotationPeriodSelected(RotationPeriod rotationPeriod) {
        if (rotationPeriod == RotationPeriod.MINUTELY) {
            minutelyRadioItem.isSelected();
        } else if (rotationPeriod == RotationPeriod.HOURLY) {
            hourlyRadioItem.isSelected();
        } else if (rotationPeriod == RotationPeriod.DAILY) {
            dailyRadioItem.isSelected();
        } else if (rotationPeriod == RotationPeriod.MONTHLY) {
            monthlyRadioItem.isSelected();
        }
    }

    //  graph handler

    @FXML
    public void checkGraphBox() {
        changeVisibleForGraphElements(graphCheckBox.isSelected());
    }

    private void changeVisibleForGraphElements(final boolean visible) {
        maxLabel.setVisible(visible);
        minLabel.setVisible(visible);
        minTextField.setVisible(visible);
    }

    @FXML
    public void closeApplication() {
        windowManager.closeWindow();
    }

    @FXML
    public void openCreditView() {
        windowManager.openWindow(ScreenType.CREDIT);
    }

    @FXML
    public void openDepositView() {
        windowManager.openWindow(ScreenType.DEPOSIT);
    }

    @FXML
    public void openBasicView() {
        windowManager.openWindow(ScreenType.BASIC);
    }

    @FXML
    public void openAboutView() {
        windowManager.openWindow(ScreenType.ABOUT);
    }

    @FXML
    public void openSettingsView() {
        windowManager.openWindow(ScreenType.SETTINGS);
    }
}
