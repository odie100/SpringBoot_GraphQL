package com.unidev.application.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class Currency {
    private String code;
    private String name;
    private String symbol;
    private Double sale_price;
    private Double purchase_price;
}
