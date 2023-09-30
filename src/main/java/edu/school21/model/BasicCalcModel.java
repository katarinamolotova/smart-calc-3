package edu.school21.model;

import edu.school21.model.helpers.Calculator;
import edu.school21.model.helpers.DataCooker;
import edu.school21.model.helpers.Parser;
import edu.school21.model.helpers.Validator;
import javafx.util.Pair;
import lombok.Getter;


import java.util.Queue;

@Getter
public class BasicCalcModel {
    public double getResult(final String inputString, final double value) {
        Validator.validateData(inputString);
        String result = DataCooker.DataCook(inputString, value);
        Queue<Pair<String, Double>> pairs = new Parser().doParsing(result);
        return Calculator.calculate(pairs);
    }
}
