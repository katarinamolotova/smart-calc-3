package edu.school21.model;

import edu.school21.model.helpers.Calculator;
import edu.school21.model.helpers.DataCooker;
import edu.school21.model.helpers.Parser;
import edu.school21.model.helpers.Validator;
import javafx.util.Pair;
import lombok.Getter;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Queue;

@Getter
public class BasicCalcModel {
    public double getResult(final String inputString, final double value) {
        Validator.validateData(inputString);
        String result = DataCooker.DataCook(inputString, value);
        Queue<Pair<String, Double>> pairs = new Parser().doParsing(result);
        return round(Calculator.calculate(pairs), 10);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
