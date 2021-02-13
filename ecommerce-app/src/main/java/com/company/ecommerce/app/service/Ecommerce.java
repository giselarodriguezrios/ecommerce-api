package com.company.ecommerce.app.service;

import com.company.ecommerce.app.exception.ServiceException;

public interface Ecommerce {
     String showProductsIdOrderBySequence() throws ServiceException;
}
