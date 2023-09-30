package edu.school21.viewmodels;

import edu.school21.enums.RotationPeriod;
import edu.school21.enums.ScreenType;
import edu.school21.model.BasicCalcModel;
import edu.school21.viewmodels.handlers.History;
import edu.school21.viewmodels.handlers.Settings;
import edu.school21.viewmodels.handlers.WindowManager;
import edu.school21.viewmodels.helpers.Validator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.math.BigDecimal;

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
    public LineChart graph;

    private WindowManager windowManager;
    private History history;

    private BasicCalcModel basicCalcModel;

    public void initialize(final Stage stage) {
        basicCalcModel = new BasicCalcModel();

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
    public void clickOnTextButton(final ActionEvent event) {
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
        final String expression = calcTextArea.getText();
        history.addExpressionToHistory(expression);
        try {
            if (graphCheckBox.isSelected()) {
                drawGraph(expression);
            } else {
                calculateResult(expression);
            }
        } catch (final Exception e) {
            final String message = e.getMessage();
            history.logError(message);
            windowManager.showErrorMessage(message);
        }
    }

    private void drawGraph(final String expression) {
        final double xBegin = Double.parseDouble(minTextField.getText());
        final double xEnd = Double.parseDouble(maxTextField.getText());
        double step = (Math.abs(xBegin) + Math.abs(xEnd)) / 100.0;
        double scale = Math.pow(10, 7);

        graph.getData().clear();
        graph.getData().add(new XYChart.Series<>(FXCollections.observableArrayList()));
        for (double xResult = xBegin; xResult <= xEnd; xResult += step) {
//            double yResult = basicCalcModel.getResult(expression , Math.ceil(xResult * scale) / scale);
            double yResult = basicCalcModel.getResult(expression , xResult);
            ((XYChart.Series) graph.getData().get(0)).getData().addAll(new XYChart.Data<>(xResult, yResult));
        }
    }

    private void calculateResult(final String expression) {
        double result = basicCalcModel.getResult(expression , Double.parseDouble(maxTextField.getText()));
        history.logInfo(String.format("Result is %f", result));
        calcTextArea.setText(String.valueOf(result));
    }

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
    public void chooseItemFromHistory(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
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
    
    private void setRadioMenuItemRotationPeriodSelected(final RotationPeriod rotationPeriod) {
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
