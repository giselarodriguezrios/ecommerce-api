package com.company.ecommerce.app.service;

import com.company.ecommerce.app.exception.ExceptionServiceType;
import com.company.ecommerce.app.exception.ServiceException;
import com.company.ecommerce.app.model.ProductModel;
import com.company.ecommerce.app.model.SizeModel;
import com.company.ecommerce.app.model.StockModel;
import com.company.ecommerce.app.utils.ConvertersUtil;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockProductService {

    @Value("${file.product}")
    private Resource resourceFileProduct;

    @Value("${file.size}")
    private Resource resourceFileSize;

    @Value("${file.stock}")
    private Resource resourceFileStock;

    private final FileReadService fileReadService;

    @Autowired
    public StockProductService(FileReadService fileReadService) {
        this.fileReadService = fileReadService;
    }
    public List<ProductModel> getProductsIdOrderBySequence(){
        return getProducts(getSize(getStock()));
    }

    public List<ProductModel> getProducts(List<SizeModel> listSize){
        List<ProductModel> listProducts = null;
        try {
            listProducts = fileReadService.readFile(resourceFileProduct).stream()
                    .map(product->{
                        Long productId = ConvertersUtil.converterStringToLong(product.get(0));
                        Long sequence = ConvertersUtil.converterStringToLong(product.get(1));
                        List<SizeModel> sizeModels = listSize.stream()
                                .filter(size-> size.getProductId().compareTo(productId)==0)
                                .collect(Collectors.toList());

                       return productId==null || sequence==null || !validateSizes(sizeModels) ? null:
                               ProductModel.builder()
                                .id(productId)
                                .sequence(sequence)
                                .listSize(sizeModels)
                                .build();
                    })
                    .filter(product-> Objects.nonNull(product))
                    .sorted(Comparator.comparing(ProductModel::getSequence))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ServiceException(ExceptionServiceType.PRODUCT_FILE_READ);
        } catch (CsvValidationException e) {
            throw new ServiceException(ExceptionServiceType.PRODUCT_FILE_READ);
        } catch (Exception e) {
            log.error(String.format("Ecommerce (%s): %s",
                    ExceptionServiceType.PRODUCT.getCode(),ExceptionServiceType.PRODUCT.getDescription()));
            throw new ServiceException(ExceptionServiceType.PRODUCT);
        }
        return listProducts;
    }

    private boolean validateSizes(List<SizeModel> sizeModels) {
        if(CollectionUtils.isEmpty(sizeModels)) return false;
        return (sizeModels.stream().noneMatch(size->size.getSpecial())) ||
                (sizeModels.stream().anyMatch(size->size.getSpecial()) && sizeModels.stream().anyMatch(size->!size.getSpecial()));
    }

    public List<SizeModel> getSize(List<StockModel> listStock){
        List<SizeModel> listSize = null;
        try {
            listSize = fileReadService.readFile(resourceFileSize).stream()
                    .map(size-> {
                        Long sizeId = ConvertersUtil.converterStringToLong(size.get(0));
                        Long productId = ConvertersUtil.converterStringToLong(size.get(1));
                        Boolean backSoon = ConvertersUtil.converterStringToBoolean(size.get(2));
                        Boolean special = ConvertersUtil.converterStringToBoolean(size.get(3));
                        StockModel stockModel = findStockBySize(listStock, sizeId);

                        return (sizeId!=null && productId!=null && backSoon!=null && special!=null && (exitsStock(stockModel) || backSoon))?
                                SizeModel.builder()
                                    .id(sizeId)
                                    .productId(productId)
                                    .backSoon(backSoon)
                                    .special(special)
                                    .stock(stockModel)
                                    .build(): null;
                    })
                    .filter(size -> Objects.nonNull(size))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ServiceException(ExceptionServiceType.SIZE_FILE_READ);
        } catch (CsvValidationException e) {
            throw new ServiceException(ExceptionServiceType.SIZE_FILE_READ);
        } catch (Exception e) {
            log.error(String.format("Ecommerce (%s): %s",
                    ExceptionServiceType.SIZE.getCode(),ExceptionServiceType.SIZE.getDescription()));
            throw new ServiceException(ExceptionServiceType.SIZE);
        }
        return listSize;
    }

    private boolean exitsStock(StockModel stockModel){
        return stockModel!=null && stockModel.getQuantity().compareTo(0)==1;
    }

    private StockModel findStockBySize( List<StockModel> listStock, Long sizeId){
        List<StockModel> stockModels = listStock.stream()
                .filter(stock-> stock.getSizeId().compareTo(sizeId)==0)
                .collect(Collectors.toList());

        return CollectionUtils.isEmpty(stockModels)? null: stockModels.get(0);
    }

    public List<StockModel> getStock(){
        List<StockModel> listStock = null;
        try {
            listStock = fileReadService.readFile(resourceFileStock).stream()
                    .map(stock->{
                        Long sizeId = ConvertersUtil.converterStringToLong(stock.get(0));
                        Integer quantity = ConvertersUtil.converterStringToInteger(stock.get(1));
                        return sizeId==null || quantity==null? null: StockModel.builder()
                                .sizeId(sizeId)
                                .quantity(quantity)
                                .build();
                    })
                    .filter(stock-> Objects.nonNull(stock))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ServiceException(ExceptionServiceType.STOCK_FILE_READ);
        } catch (CsvValidationException e) {
            throw new ServiceException(ExceptionServiceType.STOCK_FILE_READ);
        } catch (Exception e){
            log.error(String.format("Ecommerce (%s): %s",
                    ExceptionServiceType.STOCK.getCode(),ExceptionServiceType.STOCK.getDescription()));
            throw new ServiceException(ExceptionServiceType.STOCK);
        }
        return listStock;
    }

}
