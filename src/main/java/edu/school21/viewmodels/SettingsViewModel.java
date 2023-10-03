package edu.school21.viewmodels;

import edu.school21.viewmodels.handlers.Settings;
import edu.school21.viewmodels.handlers.WindowManager;
import edu.school21.viewmodels.helpers.Validator;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class SettingsViewModel implements ChildViewModel {
    public ColorPicker backgroundColor;
    public ColorPicker buttonBackgroundColor;
    public ColorPicker buttonTextColor;
    public ColorPicker buttonBorderColor;
    public TextField buttonFontSize;
    public AnchorPane anchorPane;

    private WindowManager windowManager;

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    @Override
    public void updateStyle() {
        anchorPane.getStylesheets().clear();
        anchorPane.getStylesheets().add("file:///" + Settings.STYLESHEET_FILE.getAbsolutePath().replace("\\", "/"));
    }

    @Override
    public void initialize() {
        buttonFontSize.textProperty()
                .addListener(Validator.createListenerForPositiveNumberWithLimit(buttonFontSize, 50));
        updateStyle();
    }

    public void setSettingFromProperties(Settings settings) {
        backgroundColor.setValue(Color.valueOf(settings.getBackgroundColor()));
        buttonBackgroundColor.setValue(Color.valueOf(settings.getButtonBackgroundColor()));
        buttonTextColor.setValue(Color.valueOf(settings.getButtonTextColor()));
        buttonBorderColor.setValue(Color.valueOf(settings.getButtonBorderColor()));
        final String fontSize = settings.getButtonFontSize();
        buttonFontSize.setText(fontSize.substring(0, fontSize.length() - 2));
    }

    public void saveSettings() {
        windowManager.updateSettings(
                getColor(backgroundColor),
                getColor(buttonBackgroundColor),
                getColor(buttonTextColor),
                getColor(buttonBorderColor),
                buttonFontSize.getText() + "px"
        );
        windowManager.closeSettingsViewAndUpdate();
    }

    private String getColor(ColorPicker picker) {
        String color = picker.getValue().toString();
        return "#" + color.substring(2, color.length() - 2);
    }
}
