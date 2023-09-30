package edu.school21.model;

import javafx.util.Pair;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

import static edu.school21.model.helpers.Validator.checkForDeposit;

@AllArgsConstructor
public class DepositCalcModel {
    private double result = 0;
    private double intermediateSum = 0;
    private double tempPercent = 0;
    private int sub_index = 0;
    private int addIndex = 0;
    private double add = 0;
    private double sum = 0;
    private double sub = 0;
    private int countPay = 0;
    private int countCap = 0;


    private boolean checkListAddAndSub(ArrayList<Pair<String, Pair<Integer, Double>>> list) {
        boolean result = true;
        try {
            checkForDeposit(list);
        } catch (RuntimeException e) {
            result = false;
        }
        return result;
    }

    private double resultPercent(
            double sum,
            Pair<String, Integer> amount_of_month,
            double percent,
            int capitalisation,
            int periodPay,
            int monthStart,
            ArrayList<Pair<String, Pair<Integer, Double>>> additions,
            ArrayList<Pair<String, Pair<Integer, Double>>> withdrawal
    ) {

        result = 0;
        intermediateSum = sum;
        tempPercent = 0;
        sub_index = 0;
        addIndex = 0;
        add = 0;

        int data = (amount_of_month.getValue() != 0) ? Integer.parseInt(amount_of_month.getKey()) * 12 : Integer.parseInt(amount_of_month.getKey());
        for (int i = monthStart; i < monthStart + data; i++, countCap++, countPay++) {
            int index = (i + 11) % 12;
            int days_in_month = (index == 1) ? 28 : (31 - index % 7 % 2);
            check_add_list(index, additions);
            checkSubList(index, withdrawal);
            checkCapitalisation(capitalisation);
            checkPeriodPay(periodPay);

            double expression = (intermediateSum + add - sub) / 100d * percent * days_in_month / 365;
            result += expression;
            tempPercent += expression;
        }
        return result;
    }


    private void check_add_list(int index, ArrayList<Pair<String, Pair<Integer, Double>>> additions) {
        if (addIndex < additions.size() &&
            index == additions.get(addIndex).getValue().getKey()) {
            add += additions.get(addIndex).getValue().getValue();
            addIndex++;
        }
    }

    private void checkSubList(int index, ArrayList<Pair<String, Pair<Integer, Double>>> withdrawal) {
        if (sub_index < withdrawal.size() && index == withdrawal.get(sub_index).getValue().getKey()) {
            sub += withdrawal.get(sub_index).getValue().getValue();
            sub_index++;
        }
    }

    private void checkCapitalisation(int capitalisation) {
        if (countCap == capitalisation && capitalisation != 0) {
            intermediateSum += tempPercent;
            countCap = 0;
            tempPercent = 0;
        }
    }

    private void checkPeriodPay(int periodPay) {
        if (countPay == periodPay && periodPay != 0) {
            intermediateSum = sum;
            countPay = 0;
        }
    }

    private double sumAtTheEnd(double sumBegin,
                       double resultPercent,
                       ArrayList<Pair<String, Pair<Integer, Double>>> additions,
                       ArrayList<Pair<String, Pair<Integer, Double>>> withdrawal) {

        double result = sumBegin + resultPercent;
        for (Pair<String, Pair<Integer, Double>> i : additions) {
            result += i.getValue().getValue();
        }
        for (Pair<String, Pair<Integer, Double>> i : withdrawal) {
            result -= i.getValue().getValue();
        }
        return result;
    }

    private double sumTax(double taxPercent, double resultPercent) {
        return resultPercent * (taxPercent / 100);
    }
}
