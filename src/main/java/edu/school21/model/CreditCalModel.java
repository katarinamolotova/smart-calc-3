package edu.school21.model;

import javafx.util.Pair;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CreditCalModel {
    private ArrayList<Double> everyMothPay;
    private double totalPayment;
    private double overpay;

   void everyMothPay(int type, double sum, Pair<Integer, Integer> amountOfMonth, double percent) {
       everyMothPay = new ArrayList<>();
       double sumDynam = sum;
       int data = (amountOfMonth.getValue() != 0) ? amountOfMonth.getKey() * 12 : amountOfMonth.getKey();
       double res;
       for (int i = 0; i < data; i++) {
           if (type == 1) {
               res = sum * ((percent / 100 / 12) / ( Math.pow((1 - (1 + (percent / 100 / 12))),(-1 * data))));
           } else {
               res = sum / data + sumDynam * (percent / 100 / 12);
           }
           everyMothPay.add(res);
           sumDynam -= everyMothPay.get(i);
       }
    }

    void totalPayment() {
        totalPayment = everyMothPay.get(0);
        for (int i = 1; i < everyMothPay.size(); i++) {
            totalPayment = totalPayment + everyMothPay.get(i);
        }
    }

    void overpay(double sum) {
        overpay = totalPayment - sum;
    }
}
