package edu.school21.model;

import edu.school21.model.helpers.Calculator;
import edu.school21.model.helpers.DataCooker;
import edu.school21.model.helpers.Parser;
import edu.school21.model.helpers.Validator;
import javafx.util.Pair;
import lombok.Getter;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Queue;

@Getter
public class BasicCalcModel {
    static final int AROUNDVAR = 7;

    public double getResult(final String inputString, final double value) {
        Validator.validateData(inputString);
        String result = DataCooker.DataCook(inputString, value);
        Queue<Pair<String, Double>> pairs = new Parser().doParsing(result);
        return round(Calculator.calculate(pairs));
    }

    private static double round(double value) {
        if (AROUNDVAR < 0) throw new IllegalArgumentException();
        try {
            BigDecimal bd = new BigDecimal(Double.toString(value));
            bd = bd.setScale(AROUNDVAR, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } catch (final Exception e) {
            throw new IllegalArgumentException("Something wrong");
        }
    }
}
