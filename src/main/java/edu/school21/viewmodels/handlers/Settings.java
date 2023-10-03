package edu.school21.viewmodels.handlers;

import java.io.*;
import java.util.Properties;
import java.util.stream.Collectors;

public class Settings {
    public static final String STYLESHEET_FILE_NAME = "settings/settings.css";
    public static final File STYLESHEET_FILE = new File(STYLESHEET_FILE_NAME);
    private static final String STYLE_TEMPLATE_FILE_NAME = "settings/settings_template.txt";
    private static final String STYLE_SETTINGS_FILE_NAME = "settings/settings.properties";

    private final Properties properties;
    private String styleTemplate;

    public Settings() {
        properties = new Properties();
        try {
            final InputStream stream = new FileInputStream(STYLE_SETTINGS_FILE_NAME);
            properties.load(stream);
            final FileReader fileReader = new FileReader(STYLE_TEMPLATE_FILE_NAME);
            final BufferedReader reader = new BufferedReader(fileReader);
            styleTemplate = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (final IOException e) {
            WindowManager.showErrorMessage(e.getMessage());
        }
    }

    public String getBackgroundColor() {
        return properties.getProperty("fx.background.color", "#ffffff");
    }

    public String getButtonBackgroundColor() {
        return properties.getProperty("fx.button.background.color", "#ffffff");
    }

    public String getButtonBorderColor() {
        return properties.getProperty("fx.button.border.color", "#000000");
    }

    public String getButtonTextColor() {
        return properties.getProperty("fx.button.text.color", "#000000");
    }

    public String getButtonFontSize() {
        return properties.getProperty("fx.button.font.size", "15px");
    }

    public void setStyleToProperties(
            final String backgroundColor,
            final String buttonBackgroundColor,
            final String buttonTextColor,
            final String buttonBorderColor,
            final String buttonFontSize
    ) {
        properties.setProperty("fx.background.color", backgroundColor);
        properties.setProperty("fx.button.background.color", buttonBackgroundColor);
        properties.setProperty("fx.button.border.color", buttonBorderColor);
        properties.setProperty("fx.button.text.color", buttonTextColor);
        properties.setProperty("fx.button.font.size", buttonFontSize);
    }

    public void setStyleSheetFromSettings() {
        final String style = getStyleFromProperties();
        try {
            final FileWriter fileWriter = new FileWriter(STYLESHEET_FILE_NAME);
            fileWriter.write(style);
            fileWriter.close();
        } catch (final IOException e) {
            WindowManager.showErrorMessage(e.getMessage());
        }
    }

    public void savePropertiesToFile()  {
        try {
            final OutputStream out = new FileOutputStream(STYLE_SETTINGS_FILE_NAME);
            properties.store(out, "Application settings");
        } catch (final IOException e) {
            WindowManager.showErrorMessage(e.getMessage());
        }
    }

    private String getStyleFromProperties() {
        return String.format(
                styleTemplate,
                getBackgroundColor(),
                getButtonBackgroundColor(),
                getButtonBorderColor(),
                getButtonTextColor(),
                getButtonFontSize()
        );
    }
}
