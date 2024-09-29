package com.rest.bank.repositories;

import com.rest.bank.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {


}
