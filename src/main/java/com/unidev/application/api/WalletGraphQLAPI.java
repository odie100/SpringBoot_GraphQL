package com.unidev.application.api;

import com.unidev.application.entities.Wallet;
import com.unidev.application.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class WalletGraphQLAPI {
    private final WalletRepository walletRepository;

    @QueryMapping
    private List<Wallet> userWallets(){
        return walletRepository.findAll();
    }

    @QueryMapping
    private Wallet walletById(@Argument String id){
        return this.walletRepository.findById(id).orElseThrow(()-> new RuntimeException("Wallet not found"));
    }
}
