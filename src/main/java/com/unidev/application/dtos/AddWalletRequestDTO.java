package com.unidev.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AddWalletRequestDTO {
    private Double balance;
    private String currency_code;
}
