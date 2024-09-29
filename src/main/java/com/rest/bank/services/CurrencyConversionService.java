package com.rest.bank.services;

import com.rest.bank.exceptions.InvalidCurrencyException;
import org.springframework.stereotype.Service;
import com.rest.bank.domain.entities.Currency;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private static final Map<String, BigDecimal> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD-EUR", new BigDecimal("0.85"));
        exchangeRates.put("EUR-USD", new BigDecimal("1.18"));
        exchangeRates.put("USD-GBP", new BigDecimal("0.75"));
        exchangeRates.put("GBP-USD", new BigDecimal("1.33"));
    }


    public BigDecimal getConversionRate(String fromCurrency, String toCurrency){
        String key = fromCurrency + "-" + toCurrency;
        return exchangeRates.get(key);

    }


    private void validateCurrency(String fromCurrency, String toCurrency) {
        if (fromCurrency == null || toCurrency == null) {
            throw new InvalidCurrencyException("Currency values cannot be null.");
        }

        if (!Currency.isValid(fromCurrency) || !Currency.isValid(toCurrency)) {
            throw new InvalidCurrencyException("Invalid currency: " + fromCurrency + " or " + toCurrency);
        }
    }
}
