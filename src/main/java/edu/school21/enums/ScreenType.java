package edu.school21.enums;

import java.io.File;

public enum ScreenType {
    BASIC("basic.fxml", "SmartCalc3"),
    CREDIT("credit.fxml", "CreditCalc"),
    DEPOSIT("deposit.fxml", "DepositCalc"),
    SETTINGS("settings.fxml", "Settings"),
    ABOUT("about.fxml", "About");

    private final String fileName;
    private final String title;

    ScreenType(String fileName, String title) {
        final String dir = File.separator + "forms" + File.separator;
        this.fileName = dir + fileName;
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }
}
