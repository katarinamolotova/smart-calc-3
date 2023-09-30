package edu.school21.enums;

public enum PeriodType {
    MONTH("Месяцев"),
    YEAR("Лет");

    private final String name;

    PeriodType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PeriodType getPeriodType(final String name) {
        return name.equals(PeriodType.MONTH.getName()) ?
                PeriodType.MONTH :
                PeriodType.YEAR;
    }
}
