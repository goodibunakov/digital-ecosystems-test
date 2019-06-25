package ru.goodibunakov.ecosystemstest;

import java.text.DecimalFormat;
import java.util.List;

import ru.goodibunakov.ecosystemstest.model.Currency;

public class Calculator {

    private List<Currency> list;

    public Calculator(List<Currency> list) {
        this.list = list;
    }

    public String calculate(String sourceCode, String targetCode, String sourceValue) {
        if (sourceValue.isEmpty()) return "0.0";

        double rateToRubles = 1.0;
        double rateToTarget = 1.0;
        for (Currency currency : list) {
            if (currency.getCharCode().equals(targetCode)) {
                rateToRubles = Double.valueOf(currency.getValue()) / (double) currency.getNominal();
            }
            if (currency.getCharCode().equals((sourceCode))) {
                rateToTarget = Double.valueOf(currency.getValue()) / (double) currency.getNominal();
            }
        }
        double targetValue = (Double.valueOf(sourceValue) / rateToRubles) * rateToTarget;
        return String.valueOf(new DecimalFormat("#0.0000").format(targetValue));
    }
}
