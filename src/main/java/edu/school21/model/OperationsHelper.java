package edu.school21.model;
import java.util.Objects;


public class OperationsHelper {
    public static boolean isBinaryOperation(final String op) {
        return (Objects.equals(op, "*") ||
                Objects.equals(op, "-") ||
                Objects.equals(op, "+") ||
                Objects.equals(op, "mod") ||
                Objects.equals(op, "^") ||
                Objects.equals(op, "/"));
    }

    public static boolean isUnaryOperation(final String op) {
        return (Objects.equals(op, "sin") ||
                Objects.equals(op, "cos") ||
                Objects.equals(op, "tan") ||
                Objects.equals(op, "sqrt") ||
                Objects.equals(op, "ln") ||
                Objects.equals(op, "log") ||
                Objects.equals(op, "atan") ||
                Objects.equals(op, "acos") ||
                Objects.equals(op, "asin") ||
                Objects.equals(op, "~") ||
                Objects.equals(op, "plus"));
    }

}
