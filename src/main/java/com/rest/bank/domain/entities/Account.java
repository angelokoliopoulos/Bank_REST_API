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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false, updatable = false)
    private  LocalDateTime createdAt;


    private String accountHolderName;


    @Override
    public boolean equals(final Object o) {
        if(o == this) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);

    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", accountHolderName='" + accountHolderName + '\'' +
                '}';
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}