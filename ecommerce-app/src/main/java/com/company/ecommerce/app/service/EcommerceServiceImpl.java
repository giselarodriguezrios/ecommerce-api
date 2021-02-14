package com.company.ecommerce.app.service;

import com.company.ecommerce.app.utils.ConvertersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service("showProducts")
public class EcommerceServiceImpl implements Ecommerce {

    private final StockProductService stockProductService;

    @Autowired
    public EcommerceServiceImpl(StockProductService stockProductService) {
        this.stockProductService = stockProductService;
    }

    @Override
    public String showProductsIdOrderBySequence() {
         return stockProductService.getProductsIdOrderBySequence()
                 .stream()
                 .map(product-> ConvertersUtil.converterLongToString(product.getId()))
                 .collect(Collectors.joining(","));
    }

}
