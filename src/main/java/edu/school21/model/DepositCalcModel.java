package edu.school21.model;

import edu.school21.model.helpers.Calculator;
import edu.school21.model.helpers.Parser;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DepositCalcModel {
    boolean checkListAddAndSub(ArrayList<Pair<String, Pair<Integer, Double>>> list) {
        boolean result = true;
        try {
            check_for_deposit(list);
        } catch (RuntimeException e) {
            result = false;
        }
        return result;
    }

    void check_for_deposit(std::vector<pair<string, pair<int, double>>> list) {
        for (int i = 0; i < static_cast<int>(list.size()); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (list[j].first == list[i].first &&
                    list[j].second.first == list[i].second.first)
                    throw std::invalid_argument("Error: Two additions in one month");
            }
        }

    double result_procent(string sum, Pair<String, int> amount_of_month, String procent, int capitaliz, int period_pay, String month_start, std::vector<Pair<String, Pair<int, double>>> &additions, std::vector<Pair<String, Pair<int, double>>> &withdrawal) {
        double result = 0, intermediate_sum = stod(sum), temp_procent = 0;
        int sub_index = 0, add_index = 0;
        double add = 0, sub = 0;
        int data = (amount_of_month.second != 0) ? stoi(amount_of_month.first) * 12 : stoi(amount_of_month.first);
        for (int i = stoi(month_start), count_cap = 0, count_pay = 0;
             i < stoi(month_start) + data; i++, count_cap++, count_pay++) {
            int index = (i + 11) % 12;
            int days_in_month = (index == 1) ? 28 : (31 - index % 7 % 2);
            check_add_list(add_index, index, add, additions);
            check_sub_list(sub_index, index, sub, withdrawal);
            check_capitaliz(count_cap, capitaliz, temp_procent, intermediate_sum);
            check_period_pay(count_pay, period_pay, intermediate_sum, sum);
            string expression = "(" + std::to_string(intermediate_sum) + "+" + std::to_string(add)
                    + "-" + std::to_string(sub) + ")/100*" + procent + "*" + std::to_string(days_in_month) + "/365";

            model->parser(expression);
            model->calculate();

            result += model->answer_;
            temp_procent += model->answer_;
        }
        return result;
    }

    void check_add_list(int& add_index, int index, double& add,
                                    std::vector<pair<String, pair<int, double>>> &additions) {
        if (add_index < static_cast<int>(additions.size()) && index == additions[add_index].second.first) {
            add += additions[add_index].second.second;
            add_index++;
        }
    }

    void check_sub_list(int& sub_index, int index, double& sub,
                                    std::vector<pair<String, pair<int, double>>> &withdrawal) {
        if (sub_index < static_cast<int>(withdrawal.size()) && index == withdrawal[sub_index].second.first) {
            sub += withdrawal[sub_index].second.second;
            sub_index++;
        }
    }

    void check_capitaliz(int count_cap, int capitaliz, double temp_procent,
                                     double intermediate_sum) {
//        if (count_cap == capitaliz && capitaliz) {
            intermediate_sum += temp_procent;
            count_cap = 0;
            temp_procent = 0;
//        }
    }

    void check_period_pay(int countPay, int periodPay, double intermediateSum, String sum) {
//        if (countPay == periodPay && periodPay) {
            intermediateSum = Double.parseDouble(sum);
            countPay = 0;
//        }
    }

    double sum_at_the_end(String sumBegin, double resultProcent, ArrayList<Pair<String, Pair<Integer, Double>>> additions, ArrayList<Pair<String, Pair<Integer, Double>>> withdrawal) {
        String expression = sumBegin + "+" + resultProcent;
        double result = Calculator.calculate(new Parser().doParsing(expression));

//        for (int it = additions.begin(); it != additions.end(); it++)
//            result += it->second.second;
//        for (int it = withdrawal.begin(); it != withdrawal.end(); it++)
//            result -= it->second.second;

        return result;
    }

    double sum_nalog(string nalog_procent, double result_procent) {
        string expression = std::to_string(result_procent) + "*(" + nalog_procent + "/100)";
        model->parser(expression);
        model->calculate();
        return model->answer_;
    }
}
