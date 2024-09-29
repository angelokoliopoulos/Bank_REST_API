package com.rest.bank.domain.dto;


import com.rest.bank.domain.entities.Currency;
import lombok.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDTO {
    public BigDecimal balance;
    public Currency currency;
    public String accountHolderName;
    public LocalDateTime createdAt;
}
