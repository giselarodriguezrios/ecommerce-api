package com.company.ecommerce.rest;

import com.company.ecommerce.app.exception.ExceptionServiceType;
import com.company.ecommerce.app.exception.ServiceException;
import com.company.ecommerce.app.service.Ecommerce;
import com.company.ecommerce.rest.controller.EcommerceController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EcommerceController.class})
public class EcommerceControllerTest {

    @MockBean
    @Qualifier("showProducts")
    private Ecommerce ecommerceService;

    @Autowired
    private EcommerceController ecommerceController;

    @Test
    public void itShouldReturnOfEcommerceServiceWithProductsIdOrderBySequence() {
        Mockito.when(ecommerceService.showProductsIdOrderBySequence()).thenReturn(mockProductsIdOrderBySequence());
        String response = ecommerceController.showProductsIdOrderBySequence();
        Assert.assertEquals(mockProductsIdOrderBySequence(), response);
    }

    private String mockProductsIdOrderBySequence() {
        return "5,1,3";
    }

}
