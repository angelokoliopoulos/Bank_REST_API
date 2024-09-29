package com.rest.bank.domain.dto;

import com.rest.bank.domain.entities.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDTO {


    private BigDecimal balance;

    @NotNull(message = "Currency is required")
    private Currency currency;

    @NotBlank(message = "Account holder name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Account holder name must contain only letters and spaces")
    private String accountHolderName;


}
