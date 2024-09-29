package com.rest.bank.controllers;

import com.rest.bank.domain.dto.AccountRequestDTO;
import com.rest.bank.domain.dto.AccountResponseDTO;
import com.rest.bank.domain.entities.Account;
import com.rest.bank.exceptions.ResourceNotFoundException;
import com.rest.bank.mappers.AccountMapper;
import com.rest.bank.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> getAccounts(Pageable pageable){
        Page<AccountResponseDTO> accounts = accService.findAll(pageable);
        return  new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> saveAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
      AccountResponseDTO account =   accService.save(accountRequestDTO);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountRequestDTO accountRequestDTO){
        if(!this.accService.accountExists(id)) {
           throw new ResourceNotFoundException("Account not found.");
        }
        Account accountFromRequest = AccountMapper.fromRequest(accountRequestDTO);
        accountFromRequest.setId(id);
        this.accService.save(accountFromRequest);

        return new ResponseEntity<>(AccountMapper.toResponse(accountFromRequest), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable Long id){
        Account account = accService.findOne(id);
        AccountResponseDTO accountDTO = AccountMapper.toResponse(account);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        this.accService.deleteAccount(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }





}
