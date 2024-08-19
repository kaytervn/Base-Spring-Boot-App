package com.app.utils;

import java.text.NumberFormat;

public final class ConvertUtils {
    public static int convertToCent(double amount) {
        return (int) Math.round(amount);
    }

    public static Long convertStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static String convertDoubleToString(Double value) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(value);
    }
}
