package com.rest.bank.mappers;

import com.rest.bank.domain.dto.TransactionRequestDTO;
import com.rest.bank.domain.dto.TransactionResponseDTO;
import com.rest.bank.domain.entities.Transaction;
import com.rest.bank.services.AccountService;
import com.rest.bank.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);


    public static TransactionResponseDTO toResponse(Transaction transaction){
        String payerName = transaction.getSourceAccount().getAccountHolderName();
        String beneficiaryName = transaction.getTargetAccount().getAccountHolderName();


        return TransactionResponseDTO.builder()
                .payerName(payerName)
                .beneficiaryName(beneficiaryName)
                .currency(transaction.getCurrency())
                .transferredAmount(transaction.getAmount())
                .transactionTimestamp(transaction.getTransactionDate())
                .build();
    }



    public static Transaction fromRequest(TransactionRequestDTO request, AccountService accountService  ){
        return Transaction.builder()
                .sourceAccount(accountService.findOne(request.getSourceAccountId()))
                .targetAccount(accountService.findOne(request.getTargetAccountId()))
                .transactionDate(LocalDateTime.now())
                .currency(request.getCurrency())
                .amount(request.getAmount())
                .build();
    }



}