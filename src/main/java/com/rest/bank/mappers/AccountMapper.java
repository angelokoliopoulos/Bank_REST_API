package com.rest.bank.mappers;
import com.rest.bank.domain.dto.AccountResponseDTO;
import com.rest.bank.domain.entities.Account;
import com.rest.bank.domain.dto.AccountRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


public class AccountMapper  {



    public static AccountResponseDTO toResponse(Account account) {
        return AccountResponseDTO.builder()
                .accountHolderName(account.getAccountHolderName())
                .createdAt(account.getCreatedAt())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .build();
    }


    public static Account fromRequest(AccountRequestDTO request) {
        return  Account.builder()
                .accountHolderName(request.getAccountHolderName())
                .balance(request.getBalance())
                .currency(request.getCurrency())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
