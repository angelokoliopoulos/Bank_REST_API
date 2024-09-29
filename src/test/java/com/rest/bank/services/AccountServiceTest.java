package com.rest.bank.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.rest.bank.domain.dto.AccountRequestDTO;
import com.rest.bank.domain.dto.AccountResponseDTO;
import com.rest.bank.domain.entities.Account;
import com.rest.bank.exceptions.ResourceNotFoundException;
import com.rest.bank.mappers.AccountMapper;
import com.rest.bank.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }




    @Test
    void save_shouldSaveAccount() {
        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setBalance(new BigDecimal("100.00"));

        Account account = AccountMapper.fromRequest(requestDTO);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponseDTO response = accountService.save(requestDTO);

        assertNotNull(response);
        assertEquals(new BigDecimal("100.00"), response.getBalance());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void deleteAccount_shouldThrowException_whenAccountDoesNotExist() {
        Long accountId = 1L;

        when(accountRepository.existsById(accountId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.deleteAccount(accountId);
        });

        verify(accountRepository, never()).deleteById(accountId); // Ensures delete is not called
    }
}
