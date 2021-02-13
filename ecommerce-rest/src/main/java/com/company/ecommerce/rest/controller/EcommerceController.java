package com.company.ecommerce.rest.controller;

import com.company.ecommerce.app.exception.ServiceException;
import com.company.ecommerce.app.service.Ecommerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EcommerceController {

    private Ecommerce ecommerceService;

    @Autowired
    public EcommerceController(@Qualifier("showProducts") Ecommerce ecommerceService) {
        this.ecommerceService = ecommerceService;
    }

    @RequestMapping(value= "/", method = RequestMethod.GET)
    public String showProductsIdOrderBySequence() throws ServiceException{
        return ecommerceService.showProductsIdOrderBySequence();
    }
}
