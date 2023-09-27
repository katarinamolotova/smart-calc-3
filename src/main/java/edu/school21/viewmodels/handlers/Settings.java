package edu.school21.viewmodels.handlers;

import edu.school21.Main;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.Properties;
import java.util.stream.Collectors;

// синглтон
public class Settings {
    public static final String STYLESHEET_FILE_NAME = "settings/settings.css";
    private static final String STYLE_TEMPLATE_FILE_NAME = "settings/settings_template.txt";
    private static final String STYLE_SETTINGS_FILE_NAME = "settings/settings.properties";

    private final ClassLoader classLoader;
    private final Properties properties;
    private String styleTemplate;

    public Settings() {
        classLoader = Main.class.getClassLoader();
        final InputStream stream = classLoader.getResourceAsStream(STYLE_SETTINGS_FILE_NAME);
        properties = new Properties();
        try {
            properties.load(stream);
            final FileReader fileReader = new FileReader(classLoader.getResource(STYLE_TEMPLATE_FILE_NAME).getPath());
            final BufferedReader reader = new BufferedReader(fileReader);
            styleTemplate = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (final IOException e) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
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

    // подумать
    public void setStyleSheetFromSettings() {
        String style = getStyleFromProperties();
        try {
            final FileWriter fileWriter = new FileWriter(classLoader.getResource(STYLESHEET_FILE_NAME).getPath());
            fileWriter.write(style);
            fileWriter.close();
        } catch (final IOException e) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void savePropertiesToFile()  {
        try {
            OutputStream out = new FileOutputStream(classLoader.getResource(STYLE_SETTINGS_FILE_NAME).getPath());
            properties.store(out, "Application settings");
        } catch (final IOException e) {
            e.printStackTrace();
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
