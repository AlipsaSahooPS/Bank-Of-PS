package com.example.bankapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    @Temporal(TemporalType.TIMESTAMP)
    Date transactionDate;
    String transactionType;
    private float transactionMoney;
    private float customerBalance;
    @ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;
}
