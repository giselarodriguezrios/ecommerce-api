package com.company.ecommerce.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeModel {
    private Long id;
    private Long productId;
    private Boolean backSoon;
    private Boolean special;
    private StockModel stock;
}


