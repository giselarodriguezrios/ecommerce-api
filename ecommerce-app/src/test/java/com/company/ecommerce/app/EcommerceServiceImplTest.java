package com.company.ecommerce.app;

import com.company.ecommerce.app.model.ProductModel;
import com.company.ecommerce.app.model.SizeModel;
import com.company.ecommerce.app.model.StockModel;
import com.company.ecommerce.app.service.EcommerceServiceImpl;
import com.company.ecommerce.app.service.StockProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EcommerceServiceImpl.class})
public class EcommerceServiceImplTest {

    @MockBean
    private StockProductService stockProductService;

    @Autowired
    private EcommerceServiceImpl ecommerceService;

    @Test
    public void itShouldReturnWithStockModelList() {
        Mockito.when(stockProductService.getProductsIdOrderBySequence()).thenReturn(mockProductsRequest());
        Assert.assertEquals(mockProductsResponse(), ecommerceService.showProductsIdOrderBySequence());
    }

    //Mocks
    private String mockProductsResponse(){
        return "5,1,3";
    }

    private List<ProductModel> mockProductsRequest(){
        List<ProductModel> values = new ArrayList();
        values.add(new ProductModel(5L, 6L, addSizesOfProductId5()));
        values.add(new ProductModel(1L, 10L, addSizesOfProductId1()));
        values.add(new ProductModel(3L, 15L, addSizesOfProductId3()));
        return values;
    }

    private List<SizeModel> addSizesOfProductId1() {
        List<SizeModel> values = new ArrayList();
        values.add(new SizeModel(11L, 1L, true, false,new StockModel(11L, 0)));
        values.add(new SizeModel(13L, 1L, true, false,new StockModel(13L,0)));
        return values;
    }

    private List<SizeModel> addSizesOfProductId3() {
        List<SizeModel> values = new ArrayList();
        values.add(new SizeModel(31L, 3L, true, false,new StockModel(31L,10)));
        values.add(new SizeModel(32L, 3L, true, false, new StockModel(32L,10)));
        values.add(new SizeModel(33L, 3L, false, false,new StockModel(33L,10)));
        return values;
    }

    private List<SizeModel> addSizesOfProductId5() {
        List<SizeModel> values = new ArrayList();
        values.add(new SizeModel(51L, 5L, true, false, new StockModel(51L,10)));
        values.add(new SizeModel(52L, 5L, false, false, new StockModel(52L,10)));
        values.add(new SizeModel(53L, 5L, false, false, new StockModel(53L,10)));
        values.add(new SizeModel(54L, 5L, true, true, new StockModel(54L,10)));
        return values;
    }
}
