package com.rest.bank.controllers;
import com.rest.bank.domain.dto.TransactionRequestDTO;
import com.rest.bank.domain.dto.TransactionResponseDTO;
import com.rest.bank.domain.entities.Transaction;
import com.rest.bank.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    public final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<TransactionResponseDTO> transferMoney(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        TransactionResponseDTO transactionResponseDTO = this.transactionService.transferMoneyToAccount(transactionRequestDTO);
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactions(Pageable pageable){
        Page<TransactionResponseDTO> transactionResponseDTO = this.transactionService.getTransactions(pageable);
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/account/{account_id}")
    public ResponseEntity <Page<TransactionResponseDTO>> getTransactionsByAccountId(@PathVariable("account_id") Long id, Pageable pageable){
        Page<TransactionResponseDTO> transactionResponseDTO = this.transactionService.getTransactionsByAccountId(id, pageable);
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.OK);
    }



}
