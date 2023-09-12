package com.unidev.application.services;

import com.unidev.application.dtos.AddWalletRequestDTO;
import com.unidev.application.entities.Currency;
import com.unidev.application.entities.Wallet;
import com.unidev.application.entities.WalletTransaction;
import com.unidev.application.enums.TransactionType;
import com.unidev.application.repositories.CurrencyRepository;
import com.unidev.application.repositories.WalletRepository;
import com.unidev.application.repositories.WalletTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class WalletService {

    private final CurrencyRepository currencyRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    private List<Wallet> wallets = new ArrayList<>();

    public Wallet save(AddWalletRequestDTO requestDTO){
        Currency currency = this.currencyRepository.findById(requestDTO.getCurrency_code()).orElseThrow(
                ()-> new RuntimeException("Wallet not found"));
        Wallet wallet = Wallet.builder()
                .balance(requestDTO.getBalance())
                .id(UUID.randomUUID().toString())
                .created_at(System.currentTimeMillis())
                .user_id("user1")
                .currency(currency)
                .build();
        return this.walletRepository.save(wallet);
    }

    public void loadData() throws IOException {
        URI uri = new ClassPathResource("currency.data.csv").getURI();
        Path path = Paths.get(uri);
        List<String> lines = Files.readAllLines(path);
        for(int i=1; i<lines.size(); i++){
            String [] line = lines.get(i).split(";");
            Currency currency = Currency.builder()
                    .code(line[0])
                    .name(line[1])
                    .sale_price(Double.parseDouble(line[2]))
                    .purchase_price(Double.parseDouble(line[3]))
                    .build();
            currencyRepository.save(currency);
        }
        Stream.of("EUR","AZN","BHD","BYN").forEach(currency_code->{
            Currency currency = this.currencyRepository.findById(currency_code).orElseThrow(
                    ()-> new RuntimeException("Currency not found"));
            Wallet wallet = new Wallet();
            wallet.setBalance(10000.0);
            wallet.setCurrency(currency);
            wallet.setCreated_at(System.currentTimeMillis());
            wallet.setUser_id("user1");
            wallet.setId(UUID.randomUUID().toString());
            this.walletRepository.save(wallet);
            wallets.add(wallet);
        });
        wallets.forEach(wallet -> {

            for (int i=0 ; i<10; i++){
                WalletTransaction walletTransaction = WalletTransaction.builder()
                        .amount(Math.random() * 1000)
                        .wallet(wallet)
                        .type(Math.random()>0.5? TransactionType.DEBIT:TransactionType.CREDIT)
                        .build();

                this.walletTransactionRepository.save(walletTransaction);

                if(walletTransaction.getType().toString().equals("CREDIT")){
                    wallet.setBalance(wallet.getBalance() + walletTransaction.getAmount() );
                }else{
                    wallet.setBalance(wallet.getBalance() - walletTransaction.getAmount());
                }

                this.walletRepository.save(wallet);
            }
        });
    }
}
