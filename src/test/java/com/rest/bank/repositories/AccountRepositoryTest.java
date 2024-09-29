package com.rest.bank.repositories;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.rest.bank.domain.entities.Account;


import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void save_shouldSaveAccount(){
        Account account = Account.builder().balance(new BigDecimal("500")).build();

        Account savedAccount = accountRepository.save(account);

        assertNotNull(savedAccount.getId());
        assertEquals(new BigDecimal("500.032"),savedAccount.getBalance());
    }


    @Test
    void findById_shouldReturnAccount_whenExists(){
        Account account = Account.builder().balance(new BigDecimal("500")).build();
        Account savedAccount = accountRepository.save(account);
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());

        assertTrue(foundAccount.isPresent());
        assertEquals(savedAccount.getId(), foundAccount.get().getId());
    }

    @Test
    void deleteById_shouldDeleteRemoveAccount(){
        Account account = Account.builder().balance(new BigDecimal("500")).build();
        Account savedAccount = accountRepository.save(account);

        accountRepository.deleteById(savedAccount.getId());

        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());
        assertFalse(foundAccount.isPresent());
    }
}
