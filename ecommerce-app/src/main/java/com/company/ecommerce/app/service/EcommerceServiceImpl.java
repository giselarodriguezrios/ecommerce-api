package com.company.ecommerce.app.service;

import com.company.ecommerce.app.model.ProductModel;
import com.company.ecommerce.app.utils.ConvertersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("showProducts")
@Slf4j
public class EcommerceImpl implements Ecommerce {

    private final StockProductService stockProductService;

    @Autowired
    public EcommerceImpl(StockProductService stockProductService) {
        this.stockProductService = stockProductService;
    }

    @Override
    public String showProductsIdOrderBySequence() {
         return stockProductService.getProducts()
                 .stream()
                 .map(product-> ConvertersUtil.converterLongToString(product.getId()))
                 .collect(Collectors.joining(","));
    }
}
