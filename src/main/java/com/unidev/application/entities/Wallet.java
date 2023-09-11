package com.unidev.application.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class Wallet {
    @Id
    private String id;
    private Double balance;
    private Long created_at;
    private String user_id;
    @ManyToOne
    private Currency currency;
    @OneToMany(mappedBy = "wallet")
    private List<WalletTransaction> transactions;
}
