package edu.school21.model;

import javafx.util.Pair;
import lombok.Getter;


import java.util.Queue;

@Getter
public class BasicCalcModel {
    private boolean error;
    private String result;
    private String answer;

    public void getResult(final String inputString, final double value) {
        Validator.validateData(inputString);
        result = DataCooker.DataCook(inputString, value);
        Queue<Pair<String, Double>> pairs = new Parser().doParsing(result);
        System.out.println(Calculator.calculate(pairs));
    }
}
