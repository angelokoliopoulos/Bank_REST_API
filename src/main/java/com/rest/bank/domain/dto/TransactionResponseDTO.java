package com.rest.bank.domain.dto;

import com.rest.bank.domain.entities.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class TransactionResponseDTO {

    private String payerName;
    private String beneficiaryName;
    private BigDecimal transferredAmount;
    private Currency currency;
    private LocalDateTime transactionTimestamp;

}
