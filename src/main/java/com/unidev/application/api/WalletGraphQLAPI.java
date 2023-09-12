package com.unidev.application.api;

import com.unidev.application.dtos.AddWalletRequestDTO;
import com.unidev.application.entities.Wallet;
import com.unidev.application.entities.WalletTransaction;
import com.unidev.application.repositories.WalletRepository;
import com.unidev.application.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class WalletGraphQLAPI {
    private final WalletRepository walletRepository;
    private final WalletService walletService;

    @QueryMapping
    private List<Wallet> userWallets(){
        return walletRepository.findAll();
    }

    @QueryMapping
    private Wallet walletById(@Argument String id){
        return this.walletRepository.findById(id).orElseThrow(()-> new RuntimeException("Wallet not found"));
    }

    @MutationMapping
    private Wallet addWallet(@Argument AddWalletRequestDTO addWalletRequestDTO){
        return this.walletService.save(addWalletRequestDTO);
    }

    @MutationMapping
    private List<WalletTransaction> walletTransfer(@Argument String source_wallet_id, @Argument String destination_wallet_id, @Argument Double amount){
        return this.walletService.walletTransfer(source_wallet_id,destination_wallet_id,amount);
    }
}
