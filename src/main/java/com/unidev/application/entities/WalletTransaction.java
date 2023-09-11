package com.unidev.application.entities;

import com.unidev.application.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long timestamp;
    private Double amount;
    @ManyToOne
    private Wallet wallet;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
