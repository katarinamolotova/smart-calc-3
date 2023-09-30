package edu.school21.model;

import edu.school21.enums.CreditType;
import edu.school21.enums.PeriodType;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CreditCalcModel {
    private ArrayList<Double> everyMothPay;
    private double totalPayment;
    private double overpay;

    public void calculate(CreditType type, double sum, int amountOfMonth, PeriodType periodType, double percent) {
        everyMothPay(type, sum, amountOfMonth, periodType, percent);
        totalPayment();
        overpay(sum);
    }

   private void everyMothPay(CreditType type, double sum, int amountOfMonth, PeriodType periodType, double percent) {
       everyMothPay = new ArrayList<>();
       double sumDynam = sum;
       int data = (periodType == PeriodType.MONTH) ? amountOfMonth : amountOfMonth * 12;
       double res;
       for (int i = 0; i < data; i++) {
           if (type == CreditType.ANNUITY) {
               res = sum * ((percent / 100 / 12) / (Math.pow((1 - (1 + (percent / 100 / 12))),(-1 * data))));
           } else {
               res = sum / data + sumDynam * (percent / 100 / 12);
           }
           everyMothPay.add(res);
           sumDynam -= everyMothPay.get(i);
       }
    }

    private void totalPayment() {
        totalPayment = everyMothPay.get(0);
        for (int i = 1; i < everyMothPay.size(); i++) {
            totalPayment = totalPayment + everyMothPay.get(i);
        }
    }

    private void overpay(double sum) {
        overpay = totalPayment - sum;
    }
}
