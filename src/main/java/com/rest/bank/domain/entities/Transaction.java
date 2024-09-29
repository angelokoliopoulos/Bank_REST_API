package com.rest.bank.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "[transaction]")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id", nullable = false)
    private Account targetAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false, updatable = false)
    private  LocalDateTime transactionDate;


    @Override
    public boolean equals(Object o){
        if(this == o ) return true;
        if( o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

}
