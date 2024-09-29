package com.rest.bank.controllers;

import com.rest.bank.services.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/v1/currency-conversion")
@RequiredArgsConstructor
public class CurrencyConversionController {
    private final CurrencyConversionService currencyConversionService;



    @GetMapping("/rate")
    public ResponseEntity<BigDecimal> getConversionRate(@RequestParam String fromCurrency, @RequestParam String toCurrency){
        BigDecimal rate = currencyConversionService.getConversionRate(fromCurrency,toCurrency);
        return ResponseEntity.ok(rate);
    }

}
