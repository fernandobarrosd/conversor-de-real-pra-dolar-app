package com.fernando.brlmoneyconverter.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtils {
    public static String convertMoneyToBrFormat(BigDecimal moneyBigDecimal) {
        return NumberFormat
                .getCurrencyInstance(new Locale("pt", "BR"))
                .format(moneyBigDecimal);
    }
}
