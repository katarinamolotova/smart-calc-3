package edu.school21.enums;

public enum RotationPeriod {
    MINUTELY("Minutely"),
    HOURLY("Hourly"),
    DAILY("Daily"),
    MONTHLY("Monthly");

    final private String name;

    RotationPeriod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
