package com.rest.bank.domain.entities;

public enum Currency {

    USD, EUR, GBP, JPY, AUD, CAD;

    public static boolean isValid(String currencyCode) {
        for (Currency currency : values()) {
            if (currency.name().equalsIgnoreCase(currencyCode)) {
                return true;
            }
        }
        return false;
    }

}
