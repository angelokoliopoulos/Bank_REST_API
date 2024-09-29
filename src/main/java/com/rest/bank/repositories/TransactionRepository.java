package com.rest.bank.repositories;

import com.rest.bank.domain.entities.Account;
import com.rest.bank.domain.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {


    Page<Transaction> findBySourceAccountId(Long sourceAccountId, Pageable pageable);

}
