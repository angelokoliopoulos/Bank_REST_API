package com.rest.bank.services;

import com.rest.bank.domain.dto.AccountRequestDTO;
import com.rest.bank.domain.dto.AccountResponseDTO;
import com.rest.bank.domain.entities.Account;
import com.rest.bank.exceptions.ResourceNotFoundException;
import com.rest.bank.mappers.AccountMapper;
import com.rest.bank.repositories.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper){

        this.accountRepository = accountRepository;
    }



    public AccountResponseDTO save(AccountRequestDTO accountRequestDTO){
        Account account = AccountMapper.fromRequest(accountRequestDTO);
        account.setBalance(
                accountRequestDTO.getBalance() != null ? accountRequestDTO.getBalance() : BigDecimal.ZERO
        );
         this.accountRepository.save(account);
         return AccountMapper.toResponse(account);
    }


    public void save(Account account){
        this.accountRepository.save(account);
    }


    public Account findOne(Long id) {
        return this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    public Page<AccountResponseDTO> findAll(Pageable pageable){
        return this.accountRepository.findAll(pageable).map(
                AccountMapper::toResponse
        );
    }


    public void deleteAccount(Long id){
        assertAccountExists(id);
         this.accountRepository.deleteById(id);
    }

    private void assertAccountExists(Long id) {
        if(!accountRepository.existsById(id)){
            throw new ResourceNotFoundException("Account not found.");
        }
    }

    public boolean accountExists(Long id){
        return this.accountRepository.existsById(id);
    }




}
