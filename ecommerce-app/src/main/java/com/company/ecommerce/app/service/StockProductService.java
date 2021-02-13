package com.company.ecommerce.app.service;

import com.company.ecommerce.app.exception.ExceptionServiceType;
import com.company.ecommerce.app.exception.ServiceException;
import com.company.ecommerce.app.model.ProductModel;
import com.company.ecommerce.app.model.SizeModel;
import com.company.ecommerce.app.model.StockModel;
import com.company.ecommerce.app.utils.ConvertersUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Value("${file.product}")
    private Resource resourceFileProduct;

    @Value("${file.size}")
    private Resource resourceFileSize;

    @Value("${file.stock}")
    private Resource resourceFileStock;

    @Autowired
    public StockService() {}

    protected List<ProductModel> get(){


    }

    private List<ProductModel> getProducts(){
        List<ProductModel> listProducts = null;
        try {
            listProducts = readFile(resourceFileProduct.getFile()).stream()
                    .map(product->{
                        return ProductModel.builder()
                                .id(ConvertersUtil.converterStringToLong(product.get(0)))
                                .sequence(ConvertersUtil.converterStringToLong(product.get(1)))
                                .build();
                    })
                    .filter(product-> product.getId()!=null && product.getSequence()!=null)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ServiceException(ExceptionServiceType.FILE_READ_PRODUCT);
        } catch (CsvValidationException e) {
            throw new ServiceException(ExceptionServiceType.FILE_READ_PRODUCT);
        }
        return listProducts;
    }

    private List<SizeModel> getSize(){
        List<SizeModel> listSize = null;
        try {
            List<StockModel> listStock = getStock();
            listSize = readFile(resourceFileSize.getFile()).stream()
                    .map(size->{
                        Long sizeId = ConvertersUtil.converterStringToLong(size.get(0));

                        List<Integer> quantity = listStock.stream()
                                .filter(stock-> stock.getSizeId().compareTo(sizeId)==0)
                                .map(StockModel::getQuantity)
                                .collect(Collectors.toList());

                        return SizeModel.builder()
                                .id(sizeId)
                                .productId(ConvertersUtil.converterStringToLong(size.get(1)))
                                .backSoon(ConvertersUtil.converterStringToBoolean(size.get(2)))
                                .special(ConvertersUtil.converterStringToBoolean(size.get(3)))
                                .build();
                    })
                    .filter(size-> size.getId()!=null && size.getProductId()!=null
                            && size.getBackSoon()!=null && size.getSpecial()!=null)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ServiceException(ExceptionServiceType.FILE_READ_SIZE);
        } catch (CsvValidationException e) {
            throw new ServiceException(ExceptionServiceType.FILE_READ_SIZE);
        }
        return listSize;
    }

    private List<StockModel> getStock(){
        List<StockModel> listStock = null;
        try {
            listStock = readFile(resourceFileStock.getFile()).stream()
                    .map(stock->{
                        return StockModel.builder()
                                .sizeId(ConvertersUtil.converterStringToLong(stock.get(0)))
                                .quantity(ConvertersUtil.converterStringToInteger(stock.get(1)))
                                .build();
                    })
                    .filter(size-> size.getSizeId()!=null && size.getQuantity()!=null)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ServiceException(ExceptionServiceType.FILE_READ_STOCK);
        } catch (CsvValidationException e) {
            throw new ServiceException(ExceptionServiceType.FILE_READ_STOCK);
        }
        return listStock;
    }

    private List<List<String>> readFile(File file) throws IOException, CsvValidationException {
        List<List<String>> records = new ArrayList<>();
        CSVReader csvReader = new CSVReader(new FileReader(file));
        String[] values = null;
        while ((values = csvReader.readNext()) != null) {
            records.add(Arrays.asList(values));
        }
        return records;
    }

}
