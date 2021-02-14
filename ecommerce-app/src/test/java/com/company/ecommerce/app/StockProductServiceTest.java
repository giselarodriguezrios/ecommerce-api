package com.company.ecommerce.app;

import com.company.ecommerce.app.exception.ExceptionServiceType;
import com.company.ecommerce.app.exception.ServiceException;
import com.company.ecommerce.app.model.ProductModel;
import com.company.ecommerce.app.model.SizeModel;
import com.company.ecommerce.app.model.StockModel;
import com.company.ecommerce.app.service.FileReadService;
import com.company.ecommerce.app.service.StockProductService;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StockProductService.class})
public class StockProductServiceTest {

    @MockBean
    private FileReadService fileReadService;

    @Autowired
    private StockProductService stockProductService;

    //Stock
    @Test
    public void itShouldReturnWithStockModelList() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenReturn(mockStocksFile());
        List<StockModel> response = stockProductService.getStock();
        Assert.assertEquals(mockStocks(), response);
    }

    @Test
    public void itShouldReturnFromIOExceptionToStockFileReadException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenThrow(new IOException());
        try {
            List<StockModel> response = stockProductService.getStock();
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.STOCK_FILE_READ).getCode(), e.getCode());
        }
    }

    @Test
    public void itShouldReturnFromCvsExceptionToStockFileReadException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenThrow(new CsvValidationException());
        try {
            List<StockModel> response = stockProductService.getStock();
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.STOCK_FILE_READ).getCode(), e.getCode());
        }
    }

    //Size
    @Test
    public void itShouldReturnWithSizeModelList() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenReturn(mockSizeFile());
        List<SizeModel> response = stockProductService.getSize(mockStocks());
        Assert.assertEquals(mockSizes(), response);
    }

    @Test
    public void itShouldReturnFromIOExceptionToSizeFileReadException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenThrow(new IOException());
        try {
            List<SizeModel> response = stockProductService.getSize(mockStocks());
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.SIZE_FILE_READ).getCode(), e.getCode());
        }
    }

    @Test
    public void itShouldReturnFromCvsExceptionToSizeFileReadException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenThrow(new CsvValidationException());
        try {
            List<SizeModel> response = stockProductService.getSize(mockStocks());
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.SIZE_FILE_READ).getCode(), e.getCode());
        }
    }

    @Test
    public void itShouldReturnFromExceptionToSizeException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenReturn(mockSizeFile());
        try {
            List<SizeModel> response = stockProductService.getSize(Mockito.any());
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.SIZE).getCode(), e.getCode());
        }
    }

  //Product
    @Test
    public void itShouldReturnOfStockProductServiceWithProductList() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenReturn(mockProductFile());
        List<ProductModel> response = stockProductService.getProducts(mockSizes());
        Assert.assertEquals(mockProducts(), response);
    }

    @Test
    public void itShouldReturnFromIOExceptionToProductFileReadException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenThrow(new IOException());
        try {
            List<ProductModel> response = stockProductService.getProducts(mockSizes());
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.PRODUCT_FILE_READ).getCode(), e.getCode());
        }
    }

    @Test
    public void itShouldReturnFromCvsExceptionToProductFileReadException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenThrow(new CsvValidationException());
        try {
            List<ProductModel> response = stockProductService.getProducts(mockSizes());
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.PRODUCT_FILE_READ).getCode(), e.getCode());
        }
    }

    @Test
    public void itShouldReturnFromExceptionToProductException() throws IOException, CsvValidationException {
        Mockito.when(fileReadService.readFile(Mockito.any())).thenReturn(mockSizeFile());
        try {
            List<ProductModel> response = stockProductService.getProducts(Mockito.any());
        }catch (ServiceException e){
            Assert.assertEquals(new ServiceException(ExceptionServiceType.PRODUCT).getCode(), e.getCode());
        }
    }


    //Mocks
    private List<ProductModel> mockProducts(){
        List<ProductModel> values = new ArrayList();
        values.add(new ProductModel(5L, 6L, addSizesOfProductId5()));
        values.add(new ProductModel(1L, 10L, addSizesOfProductId1()));
        values.add(new ProductModel(3L, 15L, addSizesOfProductId3()));
        return values;
    }

    private List<SizeModel> mockSizes() {
        List<SizeModel> values = new ArrayList();
        values.addAll(addSizesOfProductId1());
        values.add(new SizeModel(23L, 2L, true, true,null));
        values.addAll(addSizesOfProductId3());
        values.add(new SizeModel(44L, 4l, true, true, new StockModel(44L,10)));
        values.addAll(addSizesOfProductId5());
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

    private List<StockModel> mockStocks() {
        List<StockModel> values = new ArrayList();
        values.add(new StockModel(11L, 0));
        values.add(new StockModel(12L,0));
        values.add(new StockModel(13L,0));
        values.add(new StockModel(22L,0));
        values.add(new StockModel(31L,10));
        values.add(new StockModel(32L,10));
        values.add(new StockModel(33L,10));
        values.add(new StockModel(41L,0));
        values.add(new StockModel(42L,0));
        values.add(new StockModel(43L,0));
        values.add(new StockModel(44L,10));
        values.add(new StockModel(51L,10));
        values.add(new StockModel(52L,10));
        values.add(new StockModel(53L,10));
        values.add(new StockModel(54L,10));
        return values;
    }

    private List<List<String>> mockProductFile() {
        List<List<String>> values = new ArrayList();
        values.add(Arrays.asList(new String[]{"1", "10"}));
        values.add(Arrays.asList(new String[]{"2", "7"}));
        values.add(Arrays.asList(new String[]{"3", "15"}));
        values.add(Arrays.asList(new String[]{"4", "13"}));
        values.add(Arrays.asList(new String[]{"5", "6"}));
        return values;
    }

    private List<List<String>> mockSizeFile() {
        List<List<String>> values = new ArrayList();
        values.add(Arrays.asList(new String[]{"11", "1", "true", "false"}));
        values.add(Arrays.asList(new String[]{"12", "1", "false", "false"}));
        values.add(Arrays.asList(new String[]{"13", "1", "true", "false"}));
        values.add(Arrays.asList(new String[]{"21", "2", "false", "false"}));
        values.add(Arrays.asList(new String[]{"22", "2", "false", "false"}));
        values.add(Arrays.asList(new String[]{"23", "2", "true", "true"}));
        values.add(Arrays.asList(new String[]{"31", "3", "true", "false"}));
        values.add(Arrays.asList(new String[]{"32", "3", "true", "false"}));
        values.add(Arrays.asList(new String[]{"33", "3", "false", "false"}));
        values.add(Arrays.asList(new String[]{"41", "4", "false", "false"}));
        values.add(Arrays.asList(new String[]{"42", "4", "false", "false"}));
        values.add(Arrays.asList(new String[]{"43", "4", "false", "false"}));
        values.add(Arrays.asList(new String[]{"44", "4", "true", "true"}));
        values.add(Arrays.asList(new String[]{"51", "5", "true", "false"}));
        values.add(Arrays.asList(new String[]{"52", "5", "false", "false"}));
        values.add(Arrays.asList(new String[]{"53", "5", "false", "false"}));
        values.add(Arrays.asList(new String[]{"54", "5", "true", "true"}));
        return values;
    }

    private List<List<String>> mockStocksFile() {
        List<List<String>> values = new ArrayList();
        values.add(Arrays.asList(new String[]{"11", "0"}));
        values.add(Arrays.asList(new String[]{"12", "0"}));
        values.add(Arrays.asList(new String[]{"13", "0"}));
        values.add(Arrays.asList(new String[]{"22", "0"}));
        values.add(Arrays.asList(new String[]{"31", "10"}));
        values.add(Arrays.asList(new String[]{"32", "10"}));
        values.add(Arrays.asList(new String[]{"33", "10"}));
        values.add(Arrays.asList(new String[]{"41", "0"}));
        values.add(Arrays.asList(new String[]{"42", "0"}));
        values.add(Arrays.asList(new String[]{"43", "0"}));
        values.add(Arrays.asList(new String[]{"44", "10"}));
        values.add(Arrays.asList(new String[]{"51", "10"}));
        values.add(Arrays.asList(new String[]{"52", "10"}));
        values.add(Arrays.asList(new String[]{"53", "10"}));
        values.add(Arrays.asList(new String[]{"54", "10"}));
        return values;
    }

}