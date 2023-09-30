package edu.school21.model;

import javafx.util.Pair;
import java.util.ArrayList;

public class CreditCalModel {

    ArrayList<Double> everyMothPay(int type, double sum, Pair<Integer, Integer> amount_of_month, double percent) {
        ArrayList<Double> everyMonth = new ArrayList<>();
        double sumDynam = sum;
        int data = (amount_of_month.getValue() != 0) ? amount_of_month.getKey() * 12 : amount_of_month.getKey();
        double res;
        for (int i = 0; i < data; i++) {
            if (type == 1) {
                res = sum * ((percent / 100 / 12) / ( Math.pow((1 - (1 + (percent / 100 / 12))),(-1 * data))));
            } else {
                res = sum / data + sumDynam * (percent / 100 / 12);
            }
            everyMonth.add(res);
            sumDynam -= everyMonth.get(i);
        }
        return everyMonth;
    }

    double totalPayment(ArrayList<Double> everyMonth) {
        double temp = everyMonth.get(0);
        for (int i = 1; i < everyMonth.size(); i++) {
            temp = temp + everyMonth.get(i);
        }
        return temp;
    }

    double overpay(double totalPayment, double sum) {
        return totalPayment - sum;
    }
}
