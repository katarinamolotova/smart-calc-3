package edu.school21.enums;

public enum CreditType {
    ANNUITY("Аннуитетный"),
    DIFFERENTIATED("Дифференцированный");

    private final String name;

    CreditType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
