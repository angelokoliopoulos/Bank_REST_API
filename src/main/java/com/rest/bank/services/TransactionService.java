package com.rest.bank.services;


import com.rest.bank.domain.dto.TransactionRequestDTO;
import com.rest.bank.domain.dto.TransactionResponseDTO;
import com.rest.bank.domain.entities.Account;
import com.rest.bank.domain.entities.Transaction;
import com.rest.bank.exceptions.InsufficientFundsException;
import com.rest.bank.exceptions.InvalidAccountException;
import com.rest.bank.mappers.TransactionMapper;
import com.rest.bank.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CurrencyConversionService currencyConversionService;


    @Transactional
    public TransactionResponseDTO transferMoneyToAccount(TransactionRequestDTO request) {
        BigDecimal amountToTransfer = request.getAmount();
        if (!accountService.accountExists(request.getSourceAccountId()) || !accountService.accountExists(request.getTargetAccountId())) {
            throw new IllegalArgumentException("One or both accounts do not exist.");
        }

        Account sourceAccount = accountService.findOne(request.getSourceAccountId());

        if (!sourceAccount.getCurrency().equals(request.getCurrency())) {
            String sourceAccountCurrency = sourceAccount.getCurrency().toString();
            String targetAccountCurrency = request.getCurrency().toString();
            BigDecimal conversionRate = currencyConversionService.getConversionRate(sourceAccountCurrency, targetAccountCurrency);
            amountToTransfer = amountToTransfer.multiply(conversionRate);

        }


        if (!isBalanceSufficient(sourceAccount.getBalance(), amountToTransfer)) {
            throw new InsufficientFundsException("Insufficient balance in the source account for this transfer.");
        }

        Account targetAccount = accountService.findOne(request.getTargetAccountId());

        if (sourceAccount.equals(targetAccount)) {
            throw new InvalidAccountException("Cannot transfer to the same account.");
        }


        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(amountToTransfer));

        accountService.save(targetAccount);
        accountService.save(sourceAccount);
        Transaction savedTransaction = this.transactionRepository.save(TransactionMapper.fromRequest(request, accountService));
        return TransactionMapper.toResponse(savedTransaction);

    }


    public Page<TransactionResponseDTO> getTransactions(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(TransactionMapper::toResponse);
    }

    public Page<TransactionResponseDTO> getTransactionsByAccountId(Long id, Pageable pageable) {
        if (!accountService.accountExists(id)) {
            throw new IllegalArgumentException("Account does not exist.");
        }
        Page<Transaction> transactions = transactionRepository.findBySourceAccountId(id, pageable);
        return transactions.map(TransactionMapper::toResponse);
    }


    private boolean isBalanceSufficient(BigDecimal remainingAmount, BigDecimal transferAmount) {
        return remainingAmount.compareTo(transferAmount) >= 0;
    }
}
